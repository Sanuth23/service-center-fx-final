package bo;

import bo.custom.impl.*;

public class BoFactory {
    private static  BoFactory boFactory;
    private BoFactory(){

    }
    public static BoFactory getInstance(){
        return boFactory!=null ? boFactory : (boFactory=new BoFactory());
    }
    public<T extends SuperBo>T getBo(BoType boType){
        switch (boType){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case ITEM: return (T) new ItemBoImpl();
            case ORDER: return (T) new OrderBoImpl();
         //   case ORDER_DETAIL: return (T) new OrderDetailBoImpl();
            case USER: return (T) new UserBoImpl();
            case PART: return (T) new PartBoImpl();

        }
        return null;
    }
}
