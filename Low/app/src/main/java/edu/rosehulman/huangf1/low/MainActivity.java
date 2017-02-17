package edu.rosehulman.huangf1.low;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoginFragment.OnLoginListener {

    public static FloatingActionButton fab;
    public static Toolbar toolbar;
    private static final String SEARCH_MESSAGE = "Search";
    private static final String SEARCH_SHOW = "Show";
    private static final String SEARCH_CATEGORY = "Category";

    private ExpandableListView mCategoryList;

    private ArrayList<Category> categories = new ArrayList<Category>();
    private ArrayList<ArrayList<SubCategory>> subCategories = new ArrayList<ArrayList<SubCategory>>();
    private ArrayList<Integer> subCatCount = new ArrayList<Integer>();

    private int previousGroup;

    private DatabaseReference mTitleRef;
    OnCompleteListener mOnCompleteListener;
    private FragmentTransaction mFt;
    private ArrayList<String> mHistories = new ArrayList<>();

    public static ArrayList<Product> mSavedProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mHistories.add("Tee");
//        mHistories.add("jeans");
//        mHistories.add("shirt");
//        System.out.print(mHistories);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent barcodeIntent = new Intent(MainActivity.this, BarcodeCaptureActivity.class);
//                startActivity(barcodeIntent);
                IntentIntegrator scanIntegrator = new IntentIntegrator(MainActivity.this);
                scanIntegrator.initiateScan();
            }
        });


        Button goButton = (Button) findViewById(R.id.goButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ProductList.class);
                SearchView editText = (SearchView) findViewById(R.id.search);
                String message = editText.getQuery().toString();
                mHistories.add(message);
                intent.putExtra(SEARCH_MESSAGE, message);
                startActivity(intent);
            }
        });


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        
        getCatData();

        mCategoryList = (ExpandableListView) findViewById(R.id.expandableListView);
        mCategoryList.setAdapter(new expandableListViewAdapter(drawer.getContext(), categories, subCategories, subCatCount));

        mCategoryList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                }
                else{
                    if (groupPosition != previousGroup){
                        parent.collapseGroup(previousGroup);
                    }
                    previousGroup = groupPosition;
                    parent.expandGroup(groupPosition);
                }
                parent.smoothScrollToPosition(groupPosition);
                return true;
            }
        });

        mCategoryList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getBaseContext(), ProductList.class);
                ArrayList<SubCategory> tempList = new ArrayList<SubCategory>();
                tempList = subCategories.get(groupPosition);
                SubCategory cat = tempList.get(childPosition);
                intent.putExtra(SEARCH_CATEGORY, (Parcelable) cat);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            Intent i = new Intent(Intent.ACTION_VIEW);
            String url = "https://www.google.com/search?q=" + scanContent + "&ie=&oe=#q=" + scanContent + "&tbm=shop";
            i.setData(Uri.parse(url));
            startActivity(i);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_saved_product:
                //TODO
                Intent savedProductIntent = new Intent(this, ShowSavedProductActivity.class);
                savedProductIntent.putExtra("SAVEDPRODUCT", mSavedProducts);
                startActivity(savedProductIntent);
                return true;
            case R.id.action_search_history:
                //TODO
                Intent searchHistoryIntent = new Intent(this, ShowSearchHistoryActivity.class);
                searchHistoryIntent.putExtra("HISTORY", mHistories);
                startActivity(searchHistoryIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.cloting) {
//            Intent intent = new Intent(getBaseContext(), ProductList.class);
//            String message = "clothing";
//            boolean showSearch = false;
//            intent.putExtra(SEARCH_SHOW, showSearch);
//            intent.putExtra(SEARCH_MESSAGE, message);
//            startActivity(intent);
//
//        } else if (id == R.id.electronics) {
//            Intent intent = new Intent(getBaseContext(), ProductList.class);
//            String message = "electronics";
//            boolean showSearch = false;
//            intent.putExtra(SEARCH_SHOW, showSearch);
//            intent.putExtra(SEARCH_MESSAGE, message);
//            startActivity(intent);
//
//        } else if (id == R.id.home) {
//
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getCatData() {

        categories.clear();
        Category cat = new Category();

        cat.setCategoryCode(10);
        cat.setCategoryName("Men");

        categories.add(cat);

        cat = new Category();
        cat.setCategoryCode(20);
        cat.setCategoryName("Women");

        categories.add(cat);

        subCategories.clear();

        ArrayList<SubCategory> subcats = new ArrayList<SubCategory>();
        SubCategory subcat = new SubCategory();

        subcat.setSubcategoryCode(1001);
        subcat.setSubcategoryName("Jackets");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(1002);
        subcat.setSubcategoryName("Shirts");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(1003);
        subcat.setSubcategoryName("Shorts");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(1004);
        subcat.setSubcategoryName("Jeans");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(1005);
        subcat.setSubcategoryName("Pants");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(1006);
        subcat.setSubcategoryName("Shoes");
        subcats.add(subcat);

        subCategories.add(subcats);
        subCatCount.add(subcats.size());

        subcats = new ArrayList<SubCategory>();
        subcat = new SubCategory();

        subcat.setSubcategoryCode(2001);
        subcat.setSubcategoryName("Jackets");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(2002);
        subcat.setSubcategoryName("Shirts");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(2003);
        subcat.setSubcategoryName("Shorts");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(2004);
        subcat.setSubcategoryName("Jeans");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(2005);
        subcat.setSubcategoryName("Pants");
        subcats.add(subcat);

        subcat = new SubCategory();
        subcat.setSubcategoryCode(2006);
        subcat.setSubcategoryName("Shoes");
        subcats.add(subcat);

        subCategories.add(subcats);
        subCatCount.add(subcats.size());


        return;
    }

    @Override
    public void onLogin(String email, String password) {
    }

    @Override
    public void onGoogleLogin() {

    }

    @Override
    public void onRosefireLogin() {

    }

    public class expandableListViewAdapter extends BaseExpandableListAdapter{


        private final LayoutInflater layoutInflater;
        private final ArrayList<Category> categoryNames;
        private final ArrayList<ArrayList<SubCategory>> subcategoryNames;
        private final ArrayList<Integer> subcatCount;
        private final int counter;
        private SubCategory singleChild = new SubCategory();

        public expandableListViewAdapter(Context context, ArrayList<Category> catNames, ArrayList<ArrayList<SubCategory>> subcatNames, ArrayList<Integer> count) {
                layoutInflater = LayoutInflater.from(context);
                categoryNames = catNames;
                subcategoryNames = subcatNames;
                subcatCount = count;
                counter = catNames.size();
        }

        @Override
        public int getGroupCount() {
            return categoryNames.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return subCatCount.get(groupPosition);
        }

        @Override
        public Object getGroup(int groupPosition) {
            return categoryNames.get(groupPosition).getCategoryName();
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            ArrayList<SubCategory> templist = new ArrayList<SubCategory>();
            templist = subcategoryNames.get(groupPosition);
            return templist.get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.category_listview, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.cat_textview);
            textView.setText(getGroup(groupPosition).toString());
            textView.setTypeface(null, Typeface.BOLD);
            textView.setBackgroundResource(R.color.colorSecondaryText);
            textView.setTextColor(Color.WHITE);
            textView.setTextSize(36);


            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = layoutInflater.inflate(R.layout.subcategory_listview, parent, false);
            }

            singleChild = (SubCategory) getChild(groupPosition, childPosition);

            TextView childName = (TextView) convertView.findViewById(R.id.subcat_textview);
            childName.setText(singleChild.getSubcategoryName());
            childName.setBackgroundResource(R.color.colorIcons);
            childName.setTextColor(Color.BLACK);
            childName.setTextSize(28);

            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
