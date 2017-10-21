package FractionClient;
import FractionIDL.*;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;

public class Client {
    static Fraction fractionImpl;

    public static String toString(FractionRep fr)
    {
        return fr.numerator+"/"+fr.denominator;
    }

    public static void main(String args[])
    {
        try{
            // create and initialize the ORB
            ORB orb = ORB.init(args, null);

            // get the root naming context
            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            // resolve the Object Reference in Naming
            String name = "Fraction";
            fractionImpl = FractionHelper.narrow(ncRef.resolve_str(name));

            FractionRep lhs = new FractionRep(3,4);
            FractionRep rhs = new FractionRep(2,8);
            FractionRep ret = fractionImpl.add(lhs,rhs);
            System.out.println(toString(lhs)+" + "+toString(rhs)+" = "+toString(ret));

        } catch (Exception e) {
            System.out.println("ERROR : " + e) ;
            e.printStackTrace(System.out);
        }
    }
}
