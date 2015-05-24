package projecte.kangapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import projecte.kangapp.R;
import projecte.kangapp.adapter.viewholder.RecyclerHeaderViewHolder;
import projecte.kangapp.adapter.viewholder.RecyclerItemViewHolder;

/**
 * Created by sergi on 23/5/15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 2;
    private static final int TYPE_ITEM = 1;
    private List<CardItem> mItemList;
    private View view;

    public RecyclerAdapter(List<CardItem> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if (viewType == TYPE_ITEM) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
            return RecyclerItemViewHolder.newInstance(view);
        } else if (viewType == TYPE_HEADER) {
            view = LayoutInflater.from(context).inflate(R.layout.recycler_header, parent, false);
            return new RecyclerHeaderViewHolder(view);
        }
        throw new RuntimeException("There is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (!isPositionHeader(position)) {
            RecyclerItemViewHolder holder = (RecyclerItemViewHolder) viewHolder;
            int itemImage = mItemList.get(position - 1).getItemImageId();
            String itemName = mItemList.get(position - 1).getItemName();
            String userName = mItemList.get(position - 1).getUserName();
            String itemState = mItemList.get(position - 1).getItemState();
            String itemPrice = mItemList.get(position - 1).getItemPrice();
            String itemBeginEndDate = mItemList.get(position - 1).getItemBeginEndDate();
            holder.setItemParameters(view, itemImage, itemName, userName, itemState, itemPrice, itemBeginEndDate);
        }
    }

    public int getBasicItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return getBasicItemCount() + 1; // header
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
