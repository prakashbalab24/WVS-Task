package singledevapps.wvstask.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import singledevapps.wvstask.R;

public class NewsDescription_Activity extends AppCompatActivity {
    private TextView titleTv,descTv;
    private ImageView layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_description_);
        titleTv = (TextView)findViewById(R.id.tv_title);
        descTv = (TextView) findViewById(R.id.tv_desc);
        layout = (ImageView) findViewById(R.id.parent);
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        String bck = getIntent().getStringExtra("bck");
        if(!title.isEmpty()){
            titleTv.setText(title);
        }
        if (!desc.isEmpty()){
            descTv.setText(desc);
        }
        if (!bck.isEmpty()){
            Picasso.with(NewsDescription_Activity.this).load(bck).transform(new BlurTransformation(NewsDescription_Activity.this)).into(layout);
        }


    }
}
