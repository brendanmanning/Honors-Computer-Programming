/**
 * Converts a binary number into binary and hexadecimal, and tells how many "bits" binary it is.
 * 
 * @author (Brendan Manning) 
 * @version (8/27/2016)
 */

import java.util.*;
public class Main
{
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args)
    {
        String binary = null;
        int bit;
        if(args.length == 0)
        {
            System.out.println("type \"quit\" to exit");
            do {
                System.out.println("\nEnter Binary:");
                binary = in.nextLine();
                if(binary.toLowerCase().equals("quit"))
                {
                    System.out.println("Goodbye...");
                    break;
                } else {
                    doConversion(binary);
                }
            } while(true);
        } else {
            doConversion(args[0]);
        }
    }
    public static void doConversion(String binary)
    {
        // Validation
        if(!isBinary(binary))
        {
            System.out.println("Not a binary number");
        } else {
            if(binary.length() % 4 == 0)
            {
                System.out.println("You gave a " + binary.length() + " bit binary number");
                System.out.println("As Decimal: " + binaryAsDecimal(binary));
                System.out.println("As Hexadecimal: " + binaryAsHexa(binary));
            } else {
                System.out.println("Not valid");
            }
        }
    }
    public static String binaryAsHexa(String binary)
    {
        // Get the binary as an integer
        int binaryInt = binaryAsDecimal(binary);
        
        // Add the remainder of a division by 16 until we're at 0
        String hexa = "";
        do {
            hexa += numberAsHexa("" + binaryInt % 16);
            binaryInt /= 16;
        } while(binaryInt != 0);
        
        // Return the remainders in reverse order
        String flippedString = "";
        for(int i = hexa.length() - 1; i >= 0; i--)
        {
            flippedString += Character.toString(hexa.charAt(i));
        }
        return flippedString;
    }
    public static String numberAsHexa(String num)
    {
        int asDecimal = Integer.parseInt(num);
        if(asDecimal > 9)
        {
            switch(asDecimal)
            {
               case 10: return "A";
               case 11: return "B";
               case 12: return "C";
               case 13: return "D";
               case 14: return "E";
               case 15: return "F";
               default: return "_an error occured_";
            }
        } else {
            return "" + asDecimal;
        }
    }
    public static int binaryAsDecimal(String binary)
    {
        int counter = 1; // Gets doubled each iteration
        int value = 0;
        for(int i = binary.length() - 1; i >= 0; i--)
        {
            if(Integer.parseInt(Character.toString(binary.charAt(i))) == 1)
            {
                value += counter;
            }
            
            counter *= 2;
        }
        
        return value;
    }
    public static boolean isBinary(String input)
    {
        if(input.length() > 0)
        {
            for(int i = 0; i < input.length(); i++)
            {
                /* Dear self.futureInstance,
                 *      This method always returned false and you had no clue why
                 *      Well it's because you were trying to use != on two string
                 *      Unfortunately, Java is dumb and requires the .equals() method
                 *      unline EVERY OTHER LANGUAGE YOU'VE EVER USED.
                 *      You've probably been making this mistake a lot, so yeah, that's
                 *      the answer to Java's stupidity.
                 *      
                 * Sincerely,
                 * self.pastInstance
                 */
                if((Character.toString(input.charAt(i)).equals("0") == false) && (Character.toString(input.charAt(i)).equals("1") == false))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}