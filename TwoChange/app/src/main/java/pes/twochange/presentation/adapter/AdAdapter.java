package pes.twochange.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pes.twochange.R;
import pes.twochange.domain.model.Ad;
import pes.twochange.services.ImageManager;

/**
 * Created by kredes on 03/05/2017.
 */

public class AdAdapter extends RecyclerView.Adapter<AdAdapter.AdViewHolder> implements View.OnClickListener{

    public static class AdViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "AdViewHolder";
        private static Bitmap PLACEHOLDER = null;


        private TextView title, rating, description;
        private CardView card;
        private ImageView image;

        private Ad ad;

        private Context context;
        private static final ImageManager imageManager = ImageManager.getInstance();



        public AdViewHolder(View itemView, int deviceWidth, Context context) {
            super(itemView);

            this.context = context.getApplicationContext();

            if (PLACEHOLDER == null)
                PLACEHOLDER = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_icon5);

            title = (TextView) itemView.findViewById(R.id.name);
            description = (TextView) itemView.findViewById(R.id.description);
            rating = (TextView) itemView.findViewById(R.id.rating);
            image = (ImageView) itemView.findViewById(R.id.image);
            card = (CardView) itemView.findViewById(R.id.card);

            image.getLayoutParams().width = deviceWidth/2;
            image.getLayoutParams().height = deviceWidth/2;

            card.getLayoutParams().width = deviceWidth/2;
        }

        public void bindAd(final Ad ad) {
            this.ad = ad;

            title.setText(ad.getTitle());
            rating.setText(String.format("%d/100", ad.getRating()));
            description.setText(ad.getDescription());

            if (!ad.getImages().isEmpty()) {
                String path = ad.getImagesPath() + ad.getImages().get(0);
                imageManager.putImageIntoView(path, context, image);
            } else
                image.setImageBitmap(PLACEHOLDER);
        }

        public Ad getAd() {
            return ad;
        }
    }

    private List<Ad> ads;
    private Set<String> ids;

    private int deviceWidth;
    private Context context;
    private Activity activity;
    private View.OnClickListener listener;

    public AdAdapter(List<Ad> ads, int deviceWidth, Activity activity) {
        this.ads = ads;
        this.ids = new HashSet<>();
        this.deviceWidth = deviceWidth;
        this.activity = activity;
        this.context = activity.getApplicationContext();
    }

    @Override
    public AdViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_ad, parent, false);

        itemView.setOnClickListener(this);

        return new AdViewHolder(itemView, deviceWidth, activity);
    }


    @Override
    public void onBindViewHolder(AdViewHolder holder, int position) {
        Ad ad = ads.get(position);
        holder.bindAd(ad);
    }

    @Override
    public int getItemCount() {
        return ads.size();
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }


    public void add(Ad ad) {
        ads.add(ad);
        ids.add(ad.getId());
        notifyDataSetChanged();
    }

    public void update(Ad ad) {
        ads.set(ads.indexOf(ad), ad);
        notifyDataSetChanged();
    }

    public Ad getLastItem() {
        return ads.get(ads.size() - 1);
    }

    public boolean contains(String id) {
        return ids.contains(id);
    }
}
