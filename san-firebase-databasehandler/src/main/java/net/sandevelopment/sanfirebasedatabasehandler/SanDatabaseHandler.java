package net.sandevelopment.sanfirebasedatabasehandler;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

public class SanDatabaseHandler {
private Context context;
private DatabaseReference reference;
private Database database;

    public SanDatabaseHandler(Context context, DatabaseReference reference) {
        this.context = context;
        this.reference = reference;
    }

    public void createDatabaseByClass(String databaseName,Class... mClass) {
        try {
            createFromClass(databaseName,mClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void createDatabase(Database database) {
        this.database = database;
        create();
    }

    private void create(){
        if(database !=null) {
            if(database.addDatabaseName()!=null) {
                Table[] tb = database.addTable();
                if(tb!=null) {
                    Column[] columns = tb[0].getColumns();
                    if(columns !=null)
                    {
                       CreateDatabase.hold(context, reference,database.addDatabaseName(), tb);

                    } else {
                        try {
                            throw new SanFirebaseErrorException("No column was found");
                        } catch (SanFirebaseErrorException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        throw new SanFirebaseErrorException("No table was found");
                    } catch (SanFirebaseErrorException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                try {
                    throw new SanFirebaseErrorException("No database name was found");
                } catch (SanFirebaseErrorException e) {
                    e.printStackTrace();
                }
            }

        }else {
            throw new SanFirebaseErrorException("You have to call createDatabase(params) method first!");
        }
    }
    private <T extends SanFireDbResult<?>> void createFromClass( final String databaseName,Class<?>[] mClass) throws IllegalAccessException, InstantiationException {
        final Table[] tables = new Table[mClass.length];
        int k=0;
        for(Class<?> sClass : mClass) {
            tables[k] = Util.createTableByClass(sClass);
            k++;
        }
        createDatabase(new Database() {
            @Override
            public Table[] addTable() {
                return tables;
            }

            @Override
            public String addDatabaseName() {
                return databaseName;
            }
        });

        create();
    }

}
