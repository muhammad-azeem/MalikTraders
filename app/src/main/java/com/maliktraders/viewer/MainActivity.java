package com.maliktraders.viewer;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;

public class MainActivity extends Activity {
  final int NAVY=Color.rgb(10,18,33), PANEL=Color.rgb(19,30,49), LINE=Color.rgb(43,58,79), TEAL=Color.rgb(35,203,180), GOLD=Color.rgb(219,184,109), TEXT=Color.rgb(233,240,248), MUTED=Color.rgb(151,169,190);
  LinearLayout body; Uri folder; String tab="Dashboard"; final int PICK=41;
  int dp(int x){return (int)(x*getResources().getDisplayMetrics().density+.5f);}
  TextView t(String s,int z,int c,boolean b){TextView v=new TextView(this);v.setText(s);v.setTextSize(z);v.setTextColor(c);if(b)v.setTypeface(null,1);return v;}
  GradientDrawable bg(int c,int r){GradientDrawable g=new GradientDrawable();g.setColor(c);g.setCornerRadius(dp(r));g.setStroke(dp(1),LINE);return g;}
  @Override public void onCreate(Bundle b){super.onCreate(b);getWindow().setStatusBarColor(NAVY);getWindow().setNavigationBarColor(NAVY);String u=getPreferences(0).getString("folder",null);if(u!=null)folder=Uri.parse(u);render();}
  void render(){
    LinearLayout root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setBackgroundColor(NAVY);
    LinearLayout head=new LinearLayout(this);head.setGravity(Gravity.CENTER_VERTICAL);head.setPadding(dp(18),dp(18),dp(18),dp(14));head.setBackgroundColor(PANEL);
    TextView logo=t("MT",20,GOLD,true);logo.setGravity(17);logo.setBackground(bg(Color.rgb(35,49,69),13));head.addView(logo,new LinearLayout.LayoutParams(dp(48),dp(48)));
    LinearLayout brand=new LinearLayout(this);brand.setOrientation(LinearLayout.VERTICAL);brand.setPadding(dp(12),0,0,0);brand.addView(t("MALIK TRADERS",17,TEXT,true));brand.addView(t("ACCOUNTING CONTROL CENTER",10,MUTED,true));head.addView(brand,new LinearLayout.LayoutParams(0,-2,1));root.addView(head);
    LinearLayout title=new LinearLayout(this);title.setOrientation(LinearLayout.VERTICAL);title.setPadding(dp(18),dp(16),dp(18),dp(10));title.setBackgroundColor(PANEL);title.addView(t(tab,25,Color.WHITE,true));title.addView(t(sub(),12,MUTED,false));root.addView(title);
    ScrollView sv=new ScrollView(this);body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);body.setPadding(dp(16),dp(16),dp(16),dp(20));sv.addView(body);root.addView(sv,new LinearLayout.LayoutParams(-1,0,1));page();
    LinearLayout nav=new LinearLayout(this);nav.setPadding(dp(4),dp(8),dp(4),dp(8));nav.setBackgroundColor(PANEL);
    for(String n:new String[]{"Dashboard","Accounts","Search","Reports","Settings"}){TextView x=t(n,10,n.equals(tab)?TEAL:MUTED,n.equals(tab));x.setGravity(17);x.setPadding(2,dp(12),2,dp(12));x.setOnClickListener(v->{tab=n;render();});nav.addView(x,new LinearLayout.LayoutParams(0,dp(50),1));}root.addView(nav);setContentView(root);
  }
  String sub(){if(tab.equals("Dashboard"))return "Your business at a glance";if(tab.equals("Accounts"))return "Customer balances and account details";if(tab.equals("Search"))return "Find an account or ledger entry";if(tab.equals("Reports"))return "Receivables, payables and statements";return "Sync and app preferences";}
  LinearLayout card(){LinearLayout c=new LinearLayout(this);c.setOrientation(LinearLayout.VERTICAL);c.setPadding(dp(16),dp(16),dp(16),dp(16));c.setBackground(bg(PANEL,18));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=dp(12);body.addView(c,p);return c;}
  TextView button(String s){TextView b=t(s,13,TEAL,true);b.setGravity(17);b.setPadding(dp(12),dp(13),dp(12),dp(13));b.setBackground(bg(Color.rgb(25,39,60),13));return b;}
  void page(){
    if(tab.equals("Dashboard")) dashboard(); else if(tab.equals("Settings")) settings(); else placeholder();
  }
  void dashboard(){
    if(folder==null){LinearLayout c=card();TextView m=t("MT",32,GOLD,true);m.setGravity(17);c.addView(m);TextView h=t("Welcome to Malik Traders",22,Color.WHITE,true);h.setGravity(17);h.setPadding(0,dp(16),0,dp(8));c.addView(h);TextView p=t("Connect the Malik Traders folder from OneDrive. The app is read-only and your Excel files remain unchanged.",13,MUTED,false);p.setGravity(17);c.addView(p);TextView b=button("CHOOSE ONEDRIVE FOLDER");b.setOnClickListener(v->pick());LinearLayout.LayoutParams q=new LinearLayout.LayoutParams(-1,-2);q.topMargin=dp(18);c.addView(b,q);return;}
    LinearLayout s=card();s.addView(t("●  OneDrive folder connected",14,TEXT,true));s.addView(t("Read-only connection active",11,MUTED,false));TextView sync=button("SYNC NOW");sync.setOnClickListener(v->Toast.makeText(this,"Folder access is active. Excel data can be refreshed from OneDrive.",Toast.LENGTH_LONG).show());s.addView(sync);
    LinearLayout c=card();c.addView(t("RECEIVABLE",10,MUTED,true));c.addView(t("PKR 0",25,TEAL,true));c.addView(t("Connect your ledger files to populate balances",11,MUTED,false));
    LinearLayout d=card();d.addView(t("PAYABLE",10,MUTED,true));d.addView(t("PKR 0",25,GOLD,true));d.addView(t("No Dr/Cr turnover is shown",11,MUTED,false));
  }
  void placeholder(){LinearLayout c=card();c.addView(t(tab,18,Color.WHITE,true));c.addView(t("This section is ready for synced Malik Traders ledger data.",13,MUTED,false));if(folder==null){TextView b=button("CONNECT ONEDRIVE FOLDER");b.setOnClickListener(v->pick());c.addView(b);}}
  void settings(){LinearLayout c=card();c.addView(t("Data source",16,Color.WHITE,true));c.addView(t(folder==null?"No folder connected":"OneDrive folder connected",13,MUTED,false));TextView b=button(folder==null?"CHOOSE FOLDER":"CHANGE FOLDER");b.setOnClickListener(v->pick());c.addView(b);LinearLayout r=card();r.addView(t("READ ONLY",12,TEAL,true));r.addView(t("Malik Traders never edits or deletes your Excel source files.",13,MUTED,false));}
  void pick(){Intent i=new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION|Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);startActivityForResult(i,PICK);}
  @Override protected void onActivityResult(int r,int c,Intent d){super.onActivityResult(r,c,d);if(r==PICK&&c==RESULT_OK&&d!=null){folder=d.getData();if(folder!=null){try{getContentResolver().takePersistableUriPermission(folder,Intent.FLAG_GRANT_READ_URI_PERMISSION);}catch(Exception e){}getPreferences(0).edit().putString("folder",folder.toString()).apply();tab="Dashboard";render();}}}
}