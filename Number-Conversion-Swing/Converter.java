/**
 * Handles conversions between all types for UserView.java
 * 
 * @author (Brendan Manning) 
 * @version (9/6/16)
 * @copyright 2016 Brendan Manning
 */
import javax.swing.JOptionPane;
public class Converter
{
    // instance variables - replace the example below with your own
    public String bits;
    public String decimalValue;
    public String hexadecimalValue;
    public boolean hasError = false;
    public String errorMessage = null;
    /**
     * Constructor for objects of class Converter
     */
    public Converter(){}
    public void doConversion(String binary, String format)
    {
        // Remove spaces and such for odd formatting
        binary = numberCleaner(binary);
        
        // Less confusing when dealing with types other than binary
        String num = binary;
        
        // We can't handle negatives
        if(num.contains("-"))
        {
            this.hasError = true;
            this.errorMessage = "Input not valid (Negatives are not allowed)";
            return;
        }
        if(num.equals(""))
        {
           this.hasError = true;
           this.errorMessage = "You didn't enter anything";
           return;
        }
        if(format.equals(NumberType.BIN))
        {
            if(!isBinary(binary))
            {
                this.hasError = true;
                this.errorMessage = binary + " is not a binary number";
            } else {
                if(binary.length() % 4 == 0)
                {
                    this.hasError = false;
                    this.bits = "Binary was " + binary.length() + " bits";
                    this.decimalValue = "Binary as Decimal: " + binaryAsDecimal(binary);
                    this.hexadecimalValue = "Binary as Hexadecima: " + binaryAsHexa(binary);
                } else {
                    int newBinaryLength = binary.length();
                    while((newBinaryLength % 4) != 0)
                    {
                        newBinaryLength++;
                    }
                    // The input was all 0s and 1s, but the user provided a binary nunmber with an uncommon number of bits, ask them if we should adds zeroes to the front to make it valid
                    int response = JOptionPane.showConfirmDialog(null, "That binary number is a " + binary.length() + " bit number.\nThis is unconventional.\nAdd " + (newBinaryLength - binary.length()) + " zeroes to the front to make it a " + newBinaryLength + " bit number? \n(Reccommended)\n", "Confirmation", JOptionPane.YES_NO_OPTION);;
                    if(response == JOptionPane.YES_OPTION)
                    {
                        while(binary.length() != newBinaryLength)
                        {
                            binary = "0" + binary;
                        }
                        doConversion(binary, NumberType.BIN); // recurse
                    } else {
                        this.hasError = true;
                        this.errorMessage = "Not valid";
                    }
                }
            }
        } else if(format.equals(NumberType.OCT))
        {
            if(!isOctal(num))
            {
                this.hasError = true;
                this.errorMessage = num + " is not a valid octal";
            } else {
                this.hasError = false;
                this.bits = "Octal as Binary: " + intAsBinary(octalAsInt(num));
                this.decimalValue = "Octal as Decimal: " + octalAsInt(num);
                this.hexadecimalValue = "Octal as Hexadecimal: " + binaryAsHexa(intAsBinary(octalAsInt(num)));
            }
        } else if(format.equals(NumberType.HEX)) {
            //num = num.toUpperCase();
            if(!isHexa(num))
            {
                this.hasError = true;
                this.errorMessage = num + " is not a valid hexadecimal";
            } else {
                this.hasError = false;
                this.bits = "Hexadecimal as Binary: " + hexaAsBinary(num);
                this.decimalValue = "Hexadecimal as Decimal: " + binaryAsDecimal(hexaAsBinary(num));
                this.hexadecimalValue = "Hexadecimal as Octal: " + intAsOctal(binaryAsDecimal(hexaAsBinary(num)));
            }
        } else if(format.equals(NumberType.DEC)) {
            if(!isNumeric(num))
            {
                this.hasError = true;
                this.errorMessage = num + " is not a number";
            } else {
                this.hasError = false;
                this.bits = "Decimal as Binary: " + intAsBinary(Integer.parseInt(num));
                this.decimalValue = "Decimal as Hexadecimal: " + binaryAsHexa(intAsBinary(Integer.parseInt(num)));
                this.hexadecimalValue = "Decimal as Octal: " + intAsOctal(Integer.parseInt(num));
            }
        }
    }
    public String binaryAsHexa(String binary)
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
    public String numberAsHexa(String num)
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
    public int binaryAsDecimal(String binary)
    {
        int counter = 1; // Gets doubled each iteration
        int value = 0;
        for(int i = binary.length() - 1; i >= 0; i--)
        {
            try {
                if(Integer.parseInt(Character.toString(binary.charAt(i))) == 1)
                {
                    value += counter;
                }
            } catch (Exception e) {
                this.hasError = true;
                this.errorMessage = "Conversion to failed because " + Character.toString(binary.charAt(i)) + " could not be converted to decimal (It it not a numeric digit)";
                return -1;
            }
            
            counter *= 2;
        }
        
        return value;
    }
    public int octalAsInt(String octal)
    {
        int value = 0;
        /* Octal Structure 
         * 512  64  8   1
         *  1    3  5   6 = (1 * 512) + (3 * 64) + (5 * 8) + (6 * 1)
         */
        int column = 1;
        for(int i = octal.length() - 1; i >= 0; i--)
        {
           int cval = (Integer.parseInt(Character.toString(octal.charAt(i))) * column);
           value += cval;
           column *= 8;
        }
        
        return value;
    }
    public String intAsOctal(int number)
    {
        return Integer.toOctalString(new Integer(number));
    }
    private boolean areAllDigitsAfterZero(int[] arr, int fromIndex)
    {
        for(int i = fromIndex; i < arr.length; i++)
        {
            if(arr[i] != 0)
                return false;
        }
        
        return true;
    }
    /*public String intAsBinary(int i)
    {
        return Integer.toString(i,2);
    }*/
    public String intAsBinary(int i)
    {
        boolean ok = false;
        int powerCount = 4; // Start with 8 4 2 1
        String binary = "";
        do {
            int[] power = new int[powerCount];
            int powerIndex = 1;
            for(int p = 0; p < powerCount; p++)
            {
                power[p] = powerIndex;
                powerIndex *= 2;
            }
            /*
             * 64  32  16  8   4   2   1 (topNumber)
             * ---------------------------------------
             * 0   1   0   1   1   0   1 (binaryNumber)
             */
            int topNumber;
            int remainder = i;
            binary = "";
            for(int x = power.length - 1; x>=0;x--)
            {
                topNumber = power[x];
                if((remainder - topNumber) >= 0)
                {
                    binary += "1";
                    remainder -= topNumber;
                } else {
                    binary += "0";
                }
            }
            if(remainder == 0)
            {
                ok = true;
            } else {
                powerCount *= 2;
            }
        } while(!ok);
        
        return binary;
    }
    public boolean isBinary(String input)
    {
        if(input.length() > 0)
        {
            for(int i = 0; i < input.length(); i++)
            {
                if((Character.toString(input.charAt(i)).equals("0") == false) && (Character.toString(input.charAt(i)).equals("1") == false))
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    public String hexaAsBinary(String hexa)
    {
        String binary = "";
        for(int i = 0; i < hexa.length(); i++)
        {
            String digit = Character.toString(hexa.charAt(i));
            if(isNumeric(digit))
            {
                binary += intAsBinary(Integer.parseInt(digit));
            } else {
                binary += hexaDigitToBinary(digit);
            }
        }
        return binary;
    }
    public  boolean isHexa(String input)
    {
        return isValid(input,new String[]{"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"});
    }
    public boolean isOctal(String input)
    {
        return isValid(input, new String[]{"0","1","2","3","4","5","6","7"});
    }
    private boolean isValid(String num, String[] inArr)
    {
        if(num.length() > 0)
        {
            for(int i = 0; i < num.length(); i++)
            {
                if(!arrayContains(inArr,Character.toString(num.charAt(i))))
                {
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    private String hexaDigitToBinary(String digit)
    {
        if(digit.equals("A"))
        {
            return "1010";
        } else if(digit.equals("B")) {
            return "1011";
        } else if(digit.equals("C")) {
            return "1100";
        } else if(digit.equals("D")) {
            return "1101";
        } else if(digit.equals("E")) {
            return "1110";
        } else if(digit.equals("F")) {
            return "1111";
        } else { 
            return "(ERROR)";
        }
    }
    private boolean isNumeric(String s)
    {
        return(isValid(s,new String[]{"0","1","2","3","4","5","6","7","8","9"}));
    }
    public boolean arrayContains(String[] haystack, String needle)
    {
        for(String h:haystack) { if(h.equals(needle)) { return true; } } return false;
    }
    private String numberCleaner(String n)
    {
        return n.replace(" " ,"");
    }
}
