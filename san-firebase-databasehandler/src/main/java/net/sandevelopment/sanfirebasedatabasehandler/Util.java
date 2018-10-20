package net.sandevelopment.sanfirebasedatabasehandler;

import java.lang.reflect.Field;

public class Util {

     static Table createTableByClass(Class<?> sClass){
        Field[] fields = sClass.getDeclaredFields();
        String fileName = sClass.getName();
        String tbName = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        Column[] columns = new Column[fields.length];
        int i =0;
        for(Field field : fields)
        {
            columns[i] = new Column(field.getName(),field.getType());
            /*if(field.isAnnotationPresent(SetColumnAttr.class))
            {
                SetColumnAttr setColumnAttr = field.getAnnotation(SetColumnAttr.class);
                if(setColumnAttr.setPrimary()) {columns[i].setPrimary(true);}
                if(setColumnAttr.setNotNull()) { columns[i].setNotNull(true);}
                if(setColumnAttr.setUnique()){columns[i].setUnique(true);}
                if(setColumnAttr.setAutoIncrement()){columns[i].setAutoIncrease(true);}

            }*/
            i++;
        }
        return new Table(tbName,columns);
    }
}
