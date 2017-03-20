package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.provider.ContactsContract.RawContacts;
import java.lang.Object;
import java.util.ArrayList;

import static android.R.attr.visible;

public class ContactsManagerActivity extends AppCompatActivity {

    EditText name = (EditText) findViewById(R.id.editText_name);
    EditText phone = (EditText) findViewById(R.id.editText_number);
    EditText email = (EditText) findViewById(R.id.editText_email);
    EditText address = (EditText) findViewById(R.id.editText_address);
    EditText jobTitle = (EditText) findViewById(R.id.editText_jobTitle);
    EditText company = (EditText) findViewById(R.id.editText_company);
    EditText website = (EditText) findViewById(R.id.editText_website);
    EditText im = (EditText) findViewById(R.id.editText_im);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        final Button showBtn = (Button) findViewById(R.id.button_show_hide);
        Button saveBtn = (Button) findViewById(R.id.button_save);
        Button cancelBtn = (Button) findViewById(R.id.button_cancel);
        final ScrollView container = (ScrollView) findViewById(R.id.scrollView2);

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(container.getVisibility() == View.INVISIBLE) {
                    container.setVisibility(View.VISIBLE);
                    showBtn.setText("HIDE ADDITIONAL FIELDS");
                }else{
                    container.setVisibility(View.INVISIBLE);
                    showBtn.setText("SHOW ADDITIONAL FIELDS");
                }
            }
        });
    }

    void save(){
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        if (name != null) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
        }
        if (phone != null) {
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
        }
        if (email != null) {
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        }
        if (address != null) {
            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
        }
        if (jobTitle != null) {
            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
        }
        if (company != null) {
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
        }
        ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
        if (website != null) {
            ContentValues websiteRow = new ContentValues();
            websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
            websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
            contactData.add(websiteRow);
        }
        if (im != null) {
            ContentValues imRow = new ContentValues();
            imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
            contactData.add(imRow);
        }
        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
        startActivity(intent);
    }
}
