package com.example.konstantin.tnews.UI.NewsListFragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.konstantin.tnews.Dagger.DependencyInjector;
import com.example.konstantin.tnews.POJO.NewsList.News;
import com.example.konstantin.tnews.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

/**
 *  Адаптер списка с заголовками новостей
 *
 * Created by Konstantin on 15.11.2017.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    @Inject Context context;

    private List<News> newsListData;
    private OnItemClickListener listener;
    private final String TAG = "tNews";

    public interface OnItemClickListener { // интерфейс листенера
        void onItemClick(News item);
    }
    public NewsListAdapter(OnItemClickListener listener) {
        this.listener = listener;
        DependencyInjector.getComponent().inject(this);
    }

    // передаем массив с актуальными данными
    public void setOrUpdateDataset (@NonNull List<News> newsList) {
        this.newsListData = newsList;
        Log.i(TAG, "Список заголовков обновлен: " + newsListData.size() + " элементов");
    }

    // создание "хранилица" для элемента с данными
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_news_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {

        // обработка клика на элементе
        holder.bind(newsListData.get(position), listener, position);

        // Дата публикации новости. Данные приводятся к локальной TimeZone и форматируются
        Date pubDate = new Date(newsListData.get(position).getDateOfPublication());
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getDefault());

        // показываем дату публикации
        holder.dateOfPublication.setText(sdf.format(pubDate));

        // заголовок
        holder.headerText.setText(Html.fromHtml(newsListData.get(position).getText()) );
    }

    @Override
    public int getItemCount() {
        return newsListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView dateOfPublication;
        private TextView headerText;

        public ViewHolder(View itemView) {
            super(itemView);
            dateOfPublication = itemView.findViewById(R.id.dateOfPublication);
            headerText = itemView.findViewById(R.id.headerText);
        }

        // обработка нажатия на элемент списка
        public void bind(final News item, final OnItemClickListener listener, final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
