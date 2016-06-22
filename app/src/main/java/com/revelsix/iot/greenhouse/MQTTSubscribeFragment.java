package com.revelsix.iot.greenhouse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MQTTSubscribeFragment extends Fragment {

    public FloatingActionButton fabConnect;
    public FloatingActionButton fabLaunchPublish;

    public SubscribeDataPassListener mCallback;

    Sensor temperature = new Sensor();
    Sensor soil = new Sensor();
    Sensor soilMoisture = new Sensor();

    public TextView txtValueTemp;
    public TextView txtValueSoil;
    public TextView txtValueMois;

    public MQTTSubscribeFragment() {
        // Required empty public constructor
    }

    // Interface of the functions from the parent Activity that this Fragment will call
    public interface SubscribeDataPassListener {
        void launchPublishFragment(String data);

        void launchConnectFragment(String data);

        void subscribeMQTTtopic(String messageParams[]);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity activity;

        // An Activity is needed to create the interface callback, so it is cast from the context
        // This is due to the onAttach method with Activity instead of context has ben deprecated
        if (context instanceof Activity) {
            activity = (Activity) context;

            // This makes sure that the container activity has implemented
            // the callback interface. If not, it throws an exception
            try {
                mCallback = (SubscribeDataPassListener) activity;

            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement SubscribeDataPassListener");
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.devicefragment, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {

            // Create a dialog that pups up with information about the application
            final Dialog dialog = new Dialog(getActivity());

            dialog.setContentView(R.layout.about_layout);
            dialog.setTitle(R.string.aboutpatterns);

            Button btnCancel = (Button) dialog.findViewById(R.id.dismiss);
            dialog.show();

            btnCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_monitor, container, false);

        // Initialise all necessary Views, their values and onClickListener's
        fabConnect = (FloatingActionButton) rootView.findViewById(R.id.fabConnect);
        fabLaunchPublish = (FloatingActionButton) rootView.findViewById(R.id.fabPublish);
        txtValueTemp = (TextView) rootView.findViewById(R.id.txtValueTemp);
        txtValueSoil = (TextView) rootView.findViewById(R.id.txtValueSoil);
        txtValueMois = (TextView) rootView.findViewById(R.id.txtValueMois);

        fabConnect.setOnClickListener(onClickListenerMQTT);
        fabLaunchPublish.setOnClickListener(onClickListenerMQTT);

        return rootView;
    }

    /*
    This is called when landing here from another fragment (through the parent Activity)
    Therefore, the values are extracted of the arguments that have been passed onto here
    to have consistency in the UI values and update them as needed
    */
    @Override
    public void onStart() {

        super.onStart();
        Bundle args = getArguments();
        if (args != null) {
//            //textBroker.setText(args.getString(puerto));
//            topicSubscribe.setText(args.getString("topic"));
//
//            messages = args.getStringArray("messages");
//            if (messages != null) {
//                messagesAdapter.clear();
//                messagesAdapter.addAll(messages);
//                // Bind the adapter to the List View
//                listViewMessages.setAdapter(messagesAdapter);
//            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String paramsTemperature[] = {"subscribe", "1"};
        mCallback.subscribeMQTTtopic(paramsTemperature);
        String paramsSoil[] = {"subscribe", "2"};
        mCallback.subscribeMQTTtopic(paramsSoil);
        String paramsSoilMoisture[] = {"subscribe", "3"};
        mCallback.subscribeMQTTtopic(paramsSoilMoisture);

        temperature.setIdSensor(1);
        temperature.setSensor("Temperatura");
        temperature.setValue("0°C");

        soil.setIdSensor(2);
        soil.setSensor("Humedad");
        soil.setValue("0%");

        soilMoisture.setIdSensor(3);
        soilMoisture.setSensor("Humedad de suelo");
        soilMoisture.setValue("0%");
    }

    // onClickListener for all Views. The action if filtered by the name of the View
    private View.OnClickListener onClickListenerMQTT = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            switch (v.getId()) {

                case R.id.fabPublish:
                    //// Change to the Publish fragment, through the parent Activity interface
                    mCallback.launchPublishFragment("Text to pass FragmentB");
                    break;

                case R.id.fabConnect:
                    // Change to the Connect fragment, through the parent Activity interface
                    mCallback.launchConnectFragment("Text to pass FragmentB");
                    break;
            }
        }
    };

    // This method is called from the parent Activity and it has to run in the UI thread, to update
    // the itesm in the ListView
    public void updateList(String message, String topic) {
        // Bind the adapter to the List View
        Log.i(topic, message);
        switch (topic) {

            case "1":
                //// Change to the Publish fragment, through the parent Activity interface
                Log.i("Temperatura", message);
                txtValueTemp.setText(message+"°C");
                break;
            case "2":
                //// Change to the Publish fragment, through the parent Activity interface
                Log.i("Humedad", message);
                txtValueSoil.setText(message+"%");
                break;
            case "3":
                //// Change to the Publish fragment, through the parent Activity interface
                Log.i("Humedad Suelo", message);
                txtValueMois.setText(message+"%");
                break;
        }
    }
}
