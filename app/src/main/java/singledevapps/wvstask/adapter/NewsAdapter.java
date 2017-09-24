package singledevapps.wvstask.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import singledevapps.wvstask.R;
import singledevapps.wvstask.activities.NewsBrowser;
import singledevapps.wvstask.helper.NetworkHelper;
import singledevapps.wvstask.model.News;
import singledevapps.wvstask.parallaxrecyclerview.ParallaxViewHolder;
import singledevapps.wvstask.utils.Utils;

/**
 * Created by prakash on 11/6/17.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> newsList;
    private boolean showFull = false;

    public class MyViewHolder extends ParallaxViewHolder {
        public TextView title,desc;
        public RelativeLayout relativeLayout;
        public CardView parentCard;
        public TextView share,showmore,full;
        public LinearLayout more;

        @Override
        public int getParallaxImageId() {
            return R.id.background;
        }

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            relativeLayout = (RelativeLayout) view.findViewById(R.id.rl_card);
            desc = (TextView) view.findViewById(R.id.tv_desc);
            parentCard = (CardView) view.findViewById(R.id.parentCard);
            share = (TextView) view.findViewById(R.id.iv_share);
            showmore = (TextView) view.findViewById(R.id.tv_showmore);
            full = (TextView) view.findViewById(R.id.tv_full);
            more = (LinearLayout) view.findViewById(R.id.more);

        }
    }


    public NewsAdapter(Context mContext, List<News> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.desc.setText(news.getDescription());
        if (!news.getUrlToImage().isEmpty()) {
           // Picasso.with(mContext).load(news.getUrlToImage()).transform(new BlurTransformation(mContext)).into(holder.getBackgroundImage());
            Picasso.with(mContext).load(news.getUrlToImage()).resize(300,300).centerCrop().into(holder.getBackgroundImage());
        }
        holder.showmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.full.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkHelper.checkConnection(mContext)){
                    Intent intent = new Intent(mContext, NewsBrowser.class);
                    intent.putExtra("url",news.getSourceUrl());
                    mContext.startActivity(intent);
                }
                else {
                    Toast.makeText(mContext,"No Internet!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.more.getVisibility()== View.VISIBLE) {
                    holder.more.setVisibility(View.GONE);
                    holder.desc.setVisibility(View.GONE);
                    holder.title.setVisibility(View.VISIBLE);
                    holder.showmore.setText("Show More");
                    return;
                }
                holder.more.setVisibility(View.VISIBLE);
                holder.desc.setVisibility(View.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.showmore.setText("Show Less");
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.more.setVisibility(View.INVISIBLE);
                try {

                    File cachePath = new File(mContext.getCacheDir(), "images");
                    cachePath.mkdirs(); // don't forget to make the directory
                    FileOutputStream stream = new FileOutputStream(cachePath + "/"+news.getTitle().substring(0,5)+".png"); // overwrites this image every time
                    Bitmap bitmap = Utils.takeScreenShot(holder.parentCard);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    stream.close();
                    Utils.shareImage(mContext,news.getTitle().substring(0,5));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
//        holder.bck.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, NewsDescription_Activity.class);
//                intent.putExtra("title",news.getTitle());
//                intent.putExtra("desc",news.getDescription());
//                intent.putExtra("bck",news.getUrlToImage());
//                mContext.startActivity(intent);
//            }
//        });
        holder.getBackgroundImage().reuse();
        }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
 