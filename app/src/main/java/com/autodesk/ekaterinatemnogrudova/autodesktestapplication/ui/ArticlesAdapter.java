package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.ArticlesInterface;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.R;
import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.models.Article;
import com.bumptech.glide.Glide;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;
import jp.wasabeef.glide.transformations.CropTransformation;
import static com.autodesk.ekaterinatemnogrudova.autodesktestapplication.utils.Constants.DATE_PATTERN;

/**
 * Created by lirons on 11/09/2017.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.BindingHolder> implements ArticlesInterface {
    private List<Article> mArticles;
    private IArticleClicked mListener;

    public interface IArticleClicked {
        void onArticleClick(Article currentArticle);
    }

    public ArticlesAdapter(List<Article> articles, IArticleClicked articleListener) {
        mArticles = articles;
        mListener = articleListener;
    }

    @Override
    public ArticlesAdapter.BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewDataBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.layout_article_view_item, parent, false);

        ArticlesAdapter.BindingHolder holder = new ArticlesAdapter.BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        holder.setClickedListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticlesAdapter.BindingHolder holder, int position) {
        Article article = mArticles.get(position);
        ImageView image = holder.itemView.findViewById(R.id.image);
        Glide.with(image.getContext())
                .load(article.getUrlToImage()).bitmapTransform(new CropTransformation(image.getContext()))
                .into(image);
        TextView title = holder.itemView.findViewById(R.id.title);
        title.setText(article.getTitle());
        TextView date = holder.itemView.findViewById(R.id.date);
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern(DATE_PATTERN);
        String dateFormated = dtfOut.print(article.getPublishedAt());
        date.setText(dateFormated);
        TextView subTitle = holder.itemView.findViewById(R.id.sub_title);
        subTitle.setText(article.getDescription());
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (mArticles == null) {
            return 0;
        }
        return mArticles.size();
    }

    @Override
    public void onArticleClick(int position) {
        Article articleItem = mArticles.get(position);
        if (mListener != null) {
            mListener.onArticleClick(articleItem);
        }
        notifyDataSetChanged();
    }

    public class BindingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ViewDataBinding binding;
        private ArticlesInterface mClickedListener;

        public BindingHolder(View rowView) {
            super(rowView);
            rowView.setOnClickListener(this);
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        @Override
        public void onClick(View v) {

            mClickedListener.onArticleClick(getAdapterPosition());

        }

        public void setClickedListener(ArticlesInterface clickedListener) {
            mClickedListener = clickedListener;
        }
    }

    public void refreshList(List<Article> articles) {
        mArticles = articles;
        notifyDataSetChanged();
    }
}
