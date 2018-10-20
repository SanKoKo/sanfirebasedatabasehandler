package net.sandevelopment.sanfirebasedatabasehandler;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;

public class CreateDatabase {
     private Context context;
     private DatabaseReference reference;
    private String databaseName;
    private Table[] tables;

    private static CreateDatabase createDatabase;

    public CreateDatabase() {
    }

    public static CreateDatabase getInstance(){
        if(createDatabase==null){
            createDatabase = new CreateDatabase();
        }
        return createDatabase;
    }

    public static void hold(Context context, DatabaseReference reference, String databaseName, Table[] tables) {
        CreateDatabase.getInstance().context = context;
        CreateDatabase.getInstance().reference = reference;
        CreateDatabase.getInstance().databaseName = databaseName;
        CreateDatabase.getInstance().tables = tables;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public DatabaseReference getReference() {
        return reference;
    }

    public void setReference(DatabaseReference reference) {
        this.reference = reference;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public Table[] getTables() {
        return tables;
    }

    public void setTables(Table[] tables) {
        this.tables = tables;
    }
}
