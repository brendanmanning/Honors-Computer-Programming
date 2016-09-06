
/**
 * Write a description of class GUIMain here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIMain
{
    public static void main(String[] args)
    {
        UserView gui = new UserView();
        if(args.length > 0)
        {
            gui.setInputText(args[0]);
        }
        if(args.length == 2)
        {
           gui.setInputType(typeAsInt(args[1]));
        }
        if(typeAsInt(args[1]) != -1)
        {
            gui.startConversion();
        }
    }
    private static int typeAsInt(String t)
    {
        t = t.toLowerCase();
        if(t.equals("bin") || t.equals("binary"))
        {
            return 0;
        } else if(t.equals("dec") || t.equals("decimal")) {
            return 1;
        } else if(t.equals("hex") || t.equals("hexadecimal")) {
            return 2;
        } else if(t.equals("oct") || t.equals("octal")) {
            return 3;
        } else {
            return -1;
        }
    }
}
