package com.example.KopaSmartest;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.*;
import android.widget.*;

import java.util.Locale;

public class MyActivity extends Activity {
    EditText no;

    private static final int CONTACT_PICKER_RESULT = 101 ;
    private DrawerLayout mDrawerLayout;
    String phone;
    String person;
    String type;
    String deadline;
    String amount;

    String mynumber;
    public static String ME = "PhoneNumber";
    Uri uriContact;
    String contactID;
    SharedPreferences sharedpreferences;
    String phoneNO;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
  Context context;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    private String MyPREFERENCES = "pref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        sharedpreferences   = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mTitle = mDrawerTitle = getTitle();
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mPlanetTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }

    }


    public  void register()
    {
        mynumber = getNumber();
        if(sharedpreferences.contains(ME))
        {
             mynumber = sharedpreferences.getString(ME, "00000");
        }
        else
        {
            mynumber = getNumber();
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString(ME,mynumber);
            editor.commit();
        }
    }
    public String getNumber()
    {
     final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.number);
        dialog.setTitle("Please input your phone number");
         no = (EditText) dialog.findViewById(R.id.phoneNo);
        Button can = (Button) dialog.findViewById(R.id.cancel1);
        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
       Button ok = (Button) dialog.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mynumber = no.getText().toString();
            }
        });
        dialog.show();
        return mynumber;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        switch(item.getItemId()) {
            case R.id.action_websearch:
                // create intent to perform web search for this planet
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, getActionBar().getTitle());
                // catch event that there's no activity to handle intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, R.string.app_not_available, Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           // selectItem(position);
            Fragment fragment;
            switch (position)
            {
                case 0:
                    fragment = new Borrow();
                    break;
                case 1:
                    fragment =new Lend();
                    break;
                case 2:
                    fragment =new TrackIt();
                    break;
                default:
                    fragment = new TrackIt();

            }
            register();
            display(position);
            //FragmentManager fragmentManager = getFragmentManager();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
    }
    public void display(int position)
    {
        String title = "";
        String event = "";
        if(position == 0)
        {
            title = "Borrower";
            event = "Borrow";
        }
        else if(position == 1)
        {
            title = "Lender";
            event = "Lend";
        }
        else
        {

        }

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.record);
        dialog.setTitle("Fill in the Details");
        TextView handle = (TextView) dialog.findViewById(R.id.handlerlabel);
        handle.setText(title);
        type= title;
        Button handler = (Button) dialog.findViewById(R.id.handler);
        handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     pickContact();
            }
        });
         final EditText amo = (EditText) dialog.findViewById(R.id.amount);
        final EditText date = (EditText) dialog.findViewById(R.id.date1);
        Button decider = (Button) dialog.findViewById(R.id.decide);
        decider.setText(event);
        decider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person = phoneNO;
                amount = amo.getText().toString();
                phone = mynumber;
                deadline = date.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("phoneNo",phoneNO);
                bundle.putString("amount",amount);
                bundle.putString("deadline",deadline);
                Fragment frag = new Borrow();
                frag.setArguments(bundle);
                String message= "You owe me ksh "+ amount + " payable by " + deadline + " . Please pay up";
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone,null,message,null,null);
                Toast.makeText(context,"Text Sent",Toast.LENGTH_LONG).show();
               // FragmentManager fragmentManager = getFragmentManager();
               // fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();

            }
        });
        dialog.show();

    }
    public void sendRecord()
    {

    }

    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new PlanetFragment();
        Bundle args = new Bundle();
        args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
   public void pickContact()
   {
       Intent intent = new Intent ( Intent.ACTION_GET_CONTENT );
       intent.setType ( ContactsContract.Contacts.CONTENT_ITEM_TYPE );
       startActivityForResult ( intent, CONTACT_PICKER_RESULT );
   }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
      uriContact = data.getData();
      retrieveContactNumber();
    }
    private void retrieveContactNumber() {
      // grantUriPermission();
        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d("cont", "Contact ID: " + contactID);

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d("Cont", "Contact Phone Number: " + contactNumber);
        phoneNO = contactNumber;
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Fragment that appears in the "content_frame", shows a planet
     */
    public static class PlanetFragment extends Fragment {
        public static final String ARG_PLANET_NUMBER = "planet_number";

        public PlanetFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_planet, container, false);
            int i = getArguments().getInt(ARG_PLANET_NUMBER);
            String planet = getResources().getStringArray(R.array.planets_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle(planet);
            return rootView;
        }
    }
}