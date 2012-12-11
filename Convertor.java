/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package IAG0010JavaClient;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

/**
 *
 * @author Emmanuel
 */
public class Convertor {
    
    
    static public int GetIntFromByteArray(byte Buf[], int offset) throws ArrayIndexOutOfBoundsException {
         int i, Result;
         try {
             for (i = Result = 0;  i < 4;  i++)
                 Result |= ((0xFF & ((int)Buf[offset + i]))) << (i * 8);
         }
         catch (ArrayIndexOutOfBoundsException aioobe) {
             throw aioobe;
         }
         return Result;
     }
    
     static public String GetStringFromByteArray(byte Buf[], int offset, int nBytesInBuf) throws ParseException {
            int count = nBytesInBuf - offset;
            String Result = "";
            if (count < 2) {
                System.out.println("Unicode string consisting of less than 2 bytes");
                throw new ParseException("Illegal string", 0);
            }
            int nChar = 0;
            boolean EndFound = false;
            for (int i = 0;  i < count;  i += 2) {
               if (Buf[offset + i] == 0 && i != count - 1 && Buf[offset + i + 1] == 0) {
                   EndFound = true;
                   break;
               }
               else
                   nChar++;
            }
            if (!EndFound) {
                System.out.println("Unicode string does not have terminating zeroes");
                throw new ParseException("Illegal string", 0);
            }
            try {
                Result = new String(Buf, offset, nChar * 2, "UTF-16LE");
            }
            catch (UnsupportedEncodingException uee) {
                System.out.println("Unable to create string, unsupported coding exception");
                throw new ParseException("Illegal string", 0);
             }
            return Result;
     }
     
     static public void PutIntIntoByteArray(byte[] Buf, int offset, int n) throws ArrayIndexOutOfBoundsException {
          try {
             for (int i = 0;  i < 4;  i++) {
                 Buf[offset + i] = (byte)(n & 0xFF);
                 n >>= 8;
              }
          }
           catch (ArrayIndexOutOfBoundsException aioobe) {
                throw aioobe;
         }
     }
     
     static public void PutStringIntoByteArray(byte[] Buf, int offset, String str) throws ArrayIndexOutOfBoundsException {
         int i, j;
         short s;
         try {
            for (i = j = 0;  j < str.length();  j++, i+=2) {
                    s = (short)str.charAt(j);
                    Buf[i + offset] = (byte)(s & 0xFF);
                    Buf[i + offset + 1] = (byte)(s >> 8);
             }
             Buf[i + offset] = Buf[i + offset + 1] = 0;
         }
         catch (ArrayIndexOutOfBoundsException aioobe) {
             throw aioobe;
         }
    }
}
