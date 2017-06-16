package singledevapps.wvstask.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import singledevapps.wvstask.R;
import singledevapps.wvstask.activities.NewsDescription_Activity;
import singledevapps.wvstask.model.News;

/**
 * Created by prakash on 11/6/17.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private Context mContext;
    private List<News> newsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView bck;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            bck = (ImageView) view.findViewById(R.id.background);

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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.bck.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        if (!news.getUrlToImage().isEmpty()) {
            Picasso.with(mContext).load(news.getUrlToImage()).transform(new BlurTransformation(mContext)).into(holder.bck);
        }
        holder.bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDescription_Activity.class);
                intent.putExtra("title",news.getTitle());
                intent.putExtra("desc",news.getDescription());
                intent.putExtra("bck",news.getUrlToImage());
                mContext.startActivity(intent);
            }
        });
        }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
 