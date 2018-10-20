# sanfirebasedatabasehandler

How to add

1. build.gradle ထဲက allprojects မှာ အောက်ပါအတိုင်းထည့်ပါ။
	
		allprojects {
			repositories {
				...
				maven { url 'https://jitpack.io' }
			}
		}
		
2. module gradle ထဲမှာအောက်ပါအတိုင်းထည့်ပါ။

			dependencies {
		        implementation 'com.github.SanKoKo:sanfirebasedatabasehandler:Tag'
		}

3. Firebase ကိုသုံးမယ်ဆိုရင် ထည့်ရမယ့် google-services ကို project build.gradle ထဲမှာထည့်ပါ။

		classpath 'com.google.gms:google-services:4.0.1'
		
4. Firebase ထဲမှာသုံးထားတဲ့ library နဲ့ support library တွေက အမြဲလိုလို မတူဖြစ်တတ်ပါတယ်။ ဒါကြောင့် library override လုပ်ရပါမယ်။ ဒါအပြင် gms google services ကိုလည်းထည့်ရပါဦးမယ်။ module build.gradle file ရဲ့ အောက်ဆုံးမှာ အောက်ပါအတိုင်း ထည့်ပေးပါ။

		dependencies{
		.... //implementation are here
		}
		configurations.all {
		    resolutionStrategy.eachDependency {
		        DependencyResolveDetails details ->
		            def requested = details.requested
		            if (requested.group == 'com.android.support') {
		                if (!requested.name.startsWith("multidex")) {
		                    details.useVersion('26.1.0')
		                }
		            }
		    }
		}
		apply plugin: 'com.google.gms.google-services'


#Features in this library
- Firebase database ကို SQL database ကဲ့သို့အသုံးပြုနိုင်အောင် ရည်ရွယ်ပြီး ရေးထားခြင်းဖြစ်ပါတယ်။
- SQL ကဲ့သို့  create,insert,update,delete,query စသည်တို့ပါ၀င်ပါတယ်။
- Firebase နဲ့ Chatting application ရေးလိုသူတွက်ပါ အဆင်ပြေ လွယ်ကူစေရန် chatting အတွက် သီးသန့် method များ ပါ၀င်ပါတယ်။

#Usage
CRUD လုပ်မည့် object class သည် SanFireDbResult ကို extends လုပ်ရပါမည်။
မဖြစ်မနေ Object class ထဲမှာ String uid နဲ့ empty constructor ထည့်ပေးရပါမည်။
(မှတ်ချက် - uid ကိုမည်သည့် value မှ ထည့်ရန်မလိုပါ။ library မှ data auto ထည့်ပေးသွားပါမည်။)


ဥပမာ ။

		public class ChatUser extends SanFireDbResult<ChatUser> implements Serializable{
		    private String uid;
		    private String userName;
		    private String email;
		    
		    public ChatUser() {
		    }

		    public ChatUser( String userName, String email) {
		        this.userName = userName;
		        this.email = email;
		    }

		    public ChatUser(String uid, String userName, String email) {
		        this.uid = uid;
		        this.userName = userName;
		        this.email = email;
		    }

		    public String getUid() {
		        return uid;
		    }

		    public void setUid(String uid) {
		        this.uid = uid;
		    }

		    public String getUserName() {
		        return userName;
		    }

		    public void setUserName(String userName) {
		        this.userName = userName;
		    }

		    public String getEmail() {
		        return email;
		    }

		    public void setEmail(String email) {
		        this.email = email;
		    }

		}

#Create
		SanDatabaseHandler  san = new SanDatabaseHandler(this,FirebaseDatabase.getInstance().getReference());
		san.createDatabaseByClass("SampleChatting",ChatUser.class);

#Insert
insert လုပ်ရန် အောက်ပါအတိုင်း တစ်ကြောင်းတည်းသာရေးရန် လိုပါသည်။
		ChatUser mChat = new Chat("username","users@email.com);
		mChat.insert();

#update
		mChat.setEmail("others@email.com");
		mChat.update(mChat.getUid());

#delete
		mChat.delete(mChat.getUid());
		
#getList
ဥပမာ - ChatUser table ထဲရှိတာတွေအားလုံး Select လုပ်မယ်ဆိုရင်
		new SanFireDbResult<ChatUser>().getAllDataListCallback(ChatUser.class, new SanFireDbResult.CallbackResult<ChatUser>() {
		            @Override
		            public void result(final List<ChatUser> mList) {
		               // list is here. you can add to listView or recyclerView with this list. Easy humm!
					   
		            }
		        });

#whereQuery
Where အတွက်က လောလောဆယ် startWith query ပဲရပါဦးမယ်။ AND, OR စသည်ဖြင့်လည်း ထည့်လို့မရသေးပါဘူး။ Firebase database ကကို Support မပေးတာကြောင့်ပါ။ ဒါပေမယ့် နောက် library update မှာ ရအောင်လုပ်ပေးပါမယ်။

		 new SanFireDbResult<ChatUser>().getDataListByLikeQuery(ChatUser.class, "userName", "user", new SanFireDbResult.CallbackResult<ChatUser>() {
		            @Override
		            public void result(List<ChatUser> mList) {
		                 // list is here. you can add to listView or recyclerView with this list. Easy humm!
		            }
		        });
				
				
#Chatting 
Chatting အတွက် သီးသန့် method နဲ့ class များပါရှိပါတယ်။
Chat message အတွက် အောက်ပါအတိုင်းသာ ရေးရန်လိုပါသည်။ ChatMessage မှာ library တွင် အဆင်သင့်ပါသော Class ဖြစ်ပါသည်။
		
		ChatMessage chatMessage = new ChatMessage(me.getUid(),other.getUid(),edChat.getText().toString(),me.getUserName(),other.getUserName());
		            SanFireDbResult.chat(chatMessage);

Chat message များအား list အဖြစ်ယူရန် အောက်ပါအတိုင်းရေးရပါမည်။
	
		 new SanFireDbResult<ChatMessage>().getChatListCallback(me.getUid(), other.getUid(), new SanFireDbResult.CallbackResult<ChatMessage>() {
		            @Override
		            public void result(List<ChatMessage> mList) {
		               // list is here. you can add to listView or recyclerView with this list. Easy humm!
		            }
		        });
				
Sample project အနေနဲ့ Chatting application လေးရေးသားထားပါတယ်။ Auth တော့မပါသေးပါ။
အဆင်မပြေတာရှိရင်သော်လည်းကောင်း၊ အကြံပေးလိုလျှင်လည်းကောင်း ကျွန်တော့်ဆီ တိုက်ရိုက် ဆက်သွယ်နိုင်ပါတယ်။

#Credit
ဒီ Library လေးကို အသုံးပြုမယ်ဆိုရင်တော့ ကျွန်တော့်ရဲ့ Sandevelopment သင်တန်းကျောင်းကို credit ပေးစေလိုပါတယ်ဗျာ။
U San Ko Ko
Sandevelopment
https://www.facebook.com/sandevelopment