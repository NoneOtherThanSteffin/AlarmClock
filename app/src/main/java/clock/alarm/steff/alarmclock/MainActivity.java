package clock.alarm.steff.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TimePicker alarmTimePicker;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private Button btnSubmit;
    private Ringtone ringtone;
    private TextView textViewMessage;
    private EditText editTextUserMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
        Intent intent = getIntent();
        if (intent != null) {
            String getData = intent.getStringExtra(AlarmReceiver.SWITCH_ACTIVITY);
            if (getData != null && getData.equalsIgnoreCase(AlarmReceiver.SWITCH_ACTIVITY)) {
                Log.e("MainActivity", "Invoked by Broadcast Receiver");
                //alarmManager.cancel(pendingIntent);
                //Log.e("MainActivity", "AlarmManager is canceled");
                Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                if (alarmUri == null) {
                    alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                }
                ringtone = RingtoneManager.getRingtone(getApplicationContext(), alarmUri);
                ringtone.play();
            }
        }
    }

    private void setViews() {
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        btnSubmit = (Button) findViewById(R.id.buttonSubmit);
        textViewMessage = (TextView) findViewById(R.id.textViewMessage);
        editTextUserMessage = (EditText) findViewById(R.id.editTextUserMessage);
    }

    private void setListeners() {
        btnSubmit.setOnClickListener(this);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
    }

    public void OnToggleClicked(View view) {
        long time;
        if (((ToggleButton) view).isChecked()) {
            Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
            if (System.currentTimeMillis() > time) {
                if (Calendar.AM_PM == 0)
                    time = time + (1000 * 60 * 60 * 12);
                else
                    time = time + (1000 * 60 * 60 * 24);
            }
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);

        } else {
            alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSubmit: {
                if (ringtone != null) {
                    String textMessage = textViewMessage.getText().toString();
                    Log.e("MainActivity", textMessage);
                    String textUserMessage = editTextUserMessage.getText().toString();
                    if (textUserMessage != null && textUserMessage.equalsIgnoreCase(textMessage)) {
                        Log.e("MainActivity", "Text Matches :" + textMessage);
                        ringtone.stop();
                    }
                }
            }
        }
    }
}
