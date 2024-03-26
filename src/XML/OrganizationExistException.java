package XML;

public class OrganizationExistException extends RuntimeException{
    public OrganizationExistException(String message){
        super(message);
    }
}
