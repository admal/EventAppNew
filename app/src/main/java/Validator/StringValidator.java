package Validator;

/**
 * Created by Adam on 2016-01-25.
 * Validates string input.
 */
public class StringValidator
{
    /**
     * Checks whether given input is empty and returns error message as string.
     * @param inputName Name of controlled text box
     * @param input input to check
     * @return proper error message
     */
    public String isEmpty(String inputName,String input)
    {
        if(input.length() == 0)
            return inputName + " can not be empty!\n";
        return "";
    }
}
