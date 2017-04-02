package ro.pub.cs.systems.eim.lab04.contactsmanager;

import android.app.Activity;
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
import android.widget.Toast;

import java.lang.Object;
import java.util.ArrayList;

import static android.R.attr.visible;

public class ContactsManagerActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText phoneEditText;
    EditText emailEditText;
    EditText addressEditText;
    EditText jobTitleEditText;
    EditText companyEditText;
    EditText websiteEditText;
    EditText imEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        nameEditText = (EditText) findViewById(R.id.editText_name);
        phoneEditText = (EditText) findViewById(R.id.editText_number);
        emailEditText = (EditText) findViewById(R.id.editText_email);
        addressEditText = (EditText) findViewById(R.id.editText_address);
        jobTitleEditText = (EditText) findViewById(R.id.editText_jobTitle);
        companyEditText = (EditText) findViewById(R.id.editText_company);
        websiteEditText = (EditText) findViewById(R.id.editText_website);
        imEditText = (EditText) findViewById(R.id.editText_im);

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone != null) {
                phoneEditText.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }

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

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String jobTitle = jobTitleEditText.getText().toString();
                String company = companyEditText.getText().toString();
                String website = websiteEditText.getText().toString();
                String im = imEditText.getText().toString();

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
                startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
