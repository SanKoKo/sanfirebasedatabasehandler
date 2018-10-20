package net.sandevelopment.sanfirebasedatabasehandler;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SanFireDbResult<T> {

    private static String chatRom = "Chat_Room";


    public void insert(){
        try {
            add();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void add() throws  ClassNotFoundException, IllegalAccessException {
        DatabaseReference ref = CreateDatabase.getInstance().getReference();
        String db = CreateDatabase.getInstance().getDatabaseName();
        String key = ref.child(db).push().getKey();

        Class clazz = Class.forName(this.getClass().getName());
        Table table = Util.createTableByClass(clazz);
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,String> inner = new HashMap<>();


        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            if(field.getName().equals("uid")){
                inner.put(field.getName(),key);
            } else {
                inner.put(field.getName(), field.get(SanFireDbResult.this).toString());
            }
            childUpdates.put(key,inner);
        }
        ref.child(db).child(table.getTbName()).updateChildren(childUpdates);
    }

    public static void chat(ChatMessage chatMessage) {
        chatting(chatMessage);
    }

    private static void chatting(final ChatMessage chatMessage) {
        final String room_type_1 = chatMessage.getChatFromUserUid() + ":" + chatMessage.getChatToUserUid();
        final String room_type_2 = chatMessage.getChatToUserUid() + ":" + chatMessage.getChatFromUserUid();
        final DatabaseReference databaseReference = CreateDatabase.getInstance().getReference();
        String db = CreateDatabase.getInstance().getDatabaseName();
        databaseReference.child(chatRom)
                .getRef()
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(room_type_1)) {
                            databaseReference.child(chatRom)
                                    .child(room_type_1)
                                    .child(String.valueOf(chatMessage.getChatTime()))
                                    .setValue(chatMessage);

                        } else if (dataSnapshot.hasChild(room_type_2)) {
                            databaseReference.child(chatRom)
                                    .child(room_type_2)
                                    .child(String.valueOf(chatMessage.getChatTime()))
                                    .setValue(chatMessage);
                        } else {
                            databaseReference.child(chatRom)
                                    .child(room_type_1)
                                    .child(String.valueOf(chatMessage.getChatTime()))
                                    .setValue(chatMessage);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Unable to send message.
                    }
                });
    }

    public void getChatListCallback(String fromUid,String toUid,CallbackResult<ChatMessage> callbackResult) {
       chattingCallback(fromUid,toUid,callbackResult);

    }

    private void chattingCallback(String fromUid, String toUid, final CallbackResult<ChatMessage> callbackResult) {
        final String room_type_1 = fromUid + ":" + toUid;
        final String room_type_2 = toUid + ":" + fromUid;
        DatabaseReference ref = CreateDatabase.getInstance().getReference();
        ref.child(chatRom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(room_type_1)){
                DatabaseReference databaseReference = CreateDatabase.getInstance().getReference();
                    databaseReference.child(chatRom).child(room_type_1).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final List<ChatMessage> list = new ArrayList<>();
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                                    list.add(chatMessage);
                                }
                                callbackResult.result(list);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else if(dataSnapshot.hasChild(room_type_2)){
                    DatabaseReference databaseReference = CreateDatabase.getInstance().getReference();
                    databaseReference.child(chatRom).child(room_type_2).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            final List<ChatMessage> list = new ArrayList<>();
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    ChatMessage chatMessage = snapshot.getValue(ChatMessage.class);
                                    list.add(chatMessage);
                                }
                                callbackResult.result(list);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }else {
                    //throw new SanFirebaseErrorException("No room like this error : "+dataSnapshot.toString());
                    System.out.println("No such room");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public interface CallbackResult<T>{
         void result(List<T> mList);
    }

    public void update(String whereUid) {

        try {
            updateData(whereUid);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void delete(String uid){
        try {
            deleteData(uid);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void deleteData(String uid) throws ClassNotFoundException {
        DatabaseReference ref = CreateDatabase.getInstance().getReference();
        String db = CreateDatabase.getInstance().getDatabaseName();
        Class clazz = Class.forName(this.getClass().getName());
        Table table = Util.createTableByClass(clazz);
        ref.child(db).child(table.getTbName()).child(uid).removeValue();
    }

    private void updateData(String whereUid) throws ClassNotFoundException, IllegalAccessException {
        DatabaseReference ref = CreateDatabase.getInstance().getReference();
        String db = CreateDatabase.getInstance().getDatabaseName();

        Class clazz = Class.forName(this.getClass().getName());
        Table table = Util.createTableByClass(clazz);
        Map<String,Object> childUpdates = new HashMap<>();
        Map<String,String> inner = new HashMap<>();


        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            if(field.getName().equals("uid")){
                inner.put(field.getName(),whereUid);
            } else {
                inner.put(field.getName(), field.get(SanFireDbResult.this).toString());
            }
            childUpdates.put(whereUid,inner);
        }
        ref.child(db).child(table.getTbName()).updateChildren(childUpdates);
    }

    public void getAllDataListCallback (Class<?> clazz,CallbackResult<T> callbackResult){
        try {
            getAllData(clazz,callbackResult);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void getAllData(Class<?> clazz, final CallbackResult<T> callbackResult) throws ClassNotFoundException {

        DatabaseReference ref = CreateDatabase.getInstance().getReference();
        String db = CreateDatabase.getInstance().getDatabaseName();
        final Table table = Util.createTableByClass(clazz);
        final HashMap<String, Class> mapper = new HashMap<>();
        final Class cls = Class.forName(clazz.getName());

        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods) {
            Class<?> type;
            String methodName;
            if(method.getName().startsWith("get")){
                type = method.getReturnType();
                methodName = "set"+method.getName().substring(3);
                mapper.put(methodName, type);
            }

        }
        ref.child(db).child(table.getTbName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<T> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        T entity = null;
                        try {
                            entity = (T) cls.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        for (String key : map.keySet()) {
                            try {
                                String method = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                                Method method1 = cls.getDeclaredMethod(method, mapper.get(method));
                                if (mapper.get(method).equals(int.class) || mapper.get(method).equals(Integer.class)) {
                                    method1.invoke(entity, Integer.parseInt(map.get(key).toString()));
                                } else {
                                    method1.invoke(entity, map.get(key).toString());
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        list.add(entity);
                    }
                callbackResult.result(list);
            }
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println(databaseError.getMessage());
            }
        });
    }

    public void getDataListByLikeQuery(Class<?> clazz,String where,String startWith,CallbackResult<T> callbackResult){
        try {
            getLikeQuery(clazz,where,startWith,callbackResult);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getLikeQuery(Class<?> clazz, String where, String startWith, final CallbackResult<T> callbackResult) throws ClassNotFoundException {
        String db = CreateDatabase.getInstance().getDatabaseName();
        final Table table = Util.createTableByClass(clazz);
        final HashMap<String, Class> mapper = new HashMap<>();
        final Class cls = Class.forName(clazz.getName());
        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods) {
            Class<?> type;
            String methodName;
            if(method.getName().startsWith("get")){
                type = method.getReturnType();
                methodName = "set"+method.getName().substring(3);
                mapper.put(methodName, type);
            }

        }
        Query query = CreateDatabase.getInstance().getReference().child(db).child(table.getTbName()).orderByChild(where).startAt(startWith).endAt(startWith+"\uf8ff");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            final List<T> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        T entity = null;
                        try {
                            entity = (T) cls.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        for (String key : map.keySet()) {
                            try {
                                String method = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                                Method method1 = cls.getDeclaredMethod(method, mapper.get(method));
                                if (mapper.get(method).equals(int.class) || mapper.get(method).equals(Integer.class)) {
                                    method1.invoke(entity, Integer.parseInt(map.get(key).toString()));
                                } else {
                                    method1.invoke(entity, map.get(key).toString());
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        list.add(entity);
                    }
                    callbackResult.result(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getDataListByQuery(Class<?> clazz,String where,String args,CallbackResult<T> callbackResult){
        try {
            getListQuery(clazz,callbackResult,where,args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void getListQuery(Class<?> clazz, final CallbackResult<T> callbackResult, String where, String args) throws ClassNotFoundException {
        String db = CreateDatabase.getInstance().getDatabaseName();
        final Table table = Util.createTableByClass(clazz);
        final HashMap<String, Class> mapper = new HashMap<>();
        final Class cls = Class.forName(clazz.getName());

        Method[] methods = cls.getDeclaredMethods();
        for(Method method : methods) {
            Class<?> type;
            String methodName;
            if(method.getName().startsWith("get")){
                type = method.getReturnType();
                methodName = "set"+method.getName().substring(3);
                mapper.put(methodName, type);
            }

        }
        Query query = CreateDatabase.getInstance().getReference().child(db).child(table.getTbName()).orderByChild(where).equalTo(args);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<T> list = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                        Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                        T entity = null;
                        try {
                            entity = (T) cls.newInstance();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        for (String key : map.keySet()) {
                            try {
                                String method = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
                                Method method1 = cls.getDeclaredMethod(method, mapper.get(method));
                                if (mapper.get(method).equals(int.class) || mapper.get(method).equals(Integer.class)) {
                                    method1.invoke(entity, Integer.parseInt(map.get(key).toString()));
                                } else {
                                    method1.invoke(entity, map.get(key).toString());
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                        list.add(entity);
                    }
                    callbackResult.result(list);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
