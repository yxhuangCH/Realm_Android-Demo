package com.gdysj.realmdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et_person_id;
    private EditText et_person_name;
    private EditText et_person_age;
    private Button btn_create;
    private Button btn_query;
    private Button btn_query_all;
    private Button btn_update;
    private Button btn_delete;
    private Button btn_reset;
    private Button btn_delete_all;
    private TextView tv_result;
    private TextView tv_result_other;

    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView(){
        et_person_id = (EditText) findViewById(R.id.et_person_id);
        et_person_name = (EditText) findViewById(R.id.et_person_name);
        et_person_age = (EditText) findViewById(R.id.et_person_age);
        btn_create = (Button) findViewById(R.id.btn_create);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_query_all = (Button) findViewById(R.id.btn_query_all);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result_other = (TextView) findViewById(R.id.tv_result_other);

        btn_create.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
        btn_delete_all.setOnClickListener(this);
        btn_query_all.setOnClickListener(this);

    }

    private void initData(){
        mRealm = Realm.getDefaultInstance();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    public void onClick(View view) {
        String id = et_person_id.getText().toString().trim();
        String personName = et_person_name.getText().toString().trim();
        String age = et_person_age.getText().toString().trim();
        int personId = 0;
        int personAge = 0;
        try {
            personId = Integer.parseInt(id);
            personAge = Integer.parseInt(age);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        switch (view.getId()){
            case R.id.btn_create:
                baseCreate(personId, personName, personAge);
                break;

            case R.id.btn_delete:
                baseDelete(personId);
                break;

            case R.id.btn_update:
                baseUpdate(personId, personName, personAge);
                break;

            case R.id.btn_query:
                baseQuery(personId);
                break;

            case R.id.btn_reset:
                reset();
                break;

            case R.id.btn_delete_all:
                baseDeleteAllPersons();
                break;

            case R.id.btn_query_all:
                baseQueryAllPersons();
                break;

            default:
                break;
        }
    }

    private void baseCreate(final int personId, final String personName, final int personAge){
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = mRealm.createObject(Person.class);
                person.setId(personId);
                person.setName(personName);
                person.setAge(personAge);
            }
        });

        RealmResults<Person> persons = mRealm.where(Person.class).findAll();
        setText(persons, tv_result);
    }

    private void baseDelete(final int personId){
        final RealmResults<Person> beforeDeletePersons = mRealm.where(Person.class).equalTo("id", personId).findAll();
        setText(beforeDeletePersons, tv_result);

        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                beforeDeletePersons.deleteFirstFromRealm();
            }
        });

        RealmResults<Person> afterDeletePersons = mRealm.where(Person.class).findAll();
        setText(afterDeletePersons, tv_result_other);
    }

    private void baseDeleteAllPersons(){
        RealmResults<Person> persons = mRealm.where(Person.class).findAll();
        mRealm.beginTransaction();
        persons.deleteAllFromRealm();
        mRealm.commitTransaction();

        setText(persons, tv_result);
    }

    private void baseQuery(int personId){
        Person person = mRealm.where(Person.class).equalTo("id", personId).findFirst();
        if (person != null){
            tv_result.setText(person.toString());
        } else {
            tv_result.setText("Person id " + personId + " is null");
        }
    }

    private void baseQueryAllPersons(){
        RealmResults<Person> persons = mRealm.where(Person.class).findAll();
        setText(persons, tv_result);
    }

    private void baseUpdate(int personId, String personName, int personAge){

        Person person = mRealm.where(Person.class).equalTo("id", personId).findFirst();
        setText(person, tv_result);

        mRealm.beginTransaction();
        person.setAge(personAge);
        person.setName(personName);
        mRealm.commitTransaction();

        setText(person, tv_result_other);
    }

    private void setText(RealmResults<Person> persons, TextView textView){
        if (persons != null && persons.size() != 0){
            StringBuilder builder = new StringBuilder("All person delete:\n");
            for (Person person : persons){
                builder.append(person.toString() + "\n");
            }
            textView.setText(builder.toString());
        } else {
            textView.setText("No persons ");
        }
    }

    private void setText(Person person, TextView textView){
        textView.setText(person.toString());
    }

    private void reset(){
        et_person_id.setText("");
        et_person_name.setText("");
        et_person_age.setText("");
        tv_result.setText("");
        tv_result_other.setText("");
    }
}
