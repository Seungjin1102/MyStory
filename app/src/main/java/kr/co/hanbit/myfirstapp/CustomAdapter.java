package kr.co.hanbit.myfirstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> implements Serializable {

    private ArrayList<ViewData> viewItems;
    private Context context;
    private ViewDBHelper viewDBHelper;


    public CustomAdapter(ArrayList<ViewData> viewItems, Context context){//생성자
        this.viewItems = viewItems;
        this.context = context;
        viewDBHelper = new ViewDBHelper(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);

        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {

            Log.d("DB", "바인드 시작");
            holder.item_title.setText(viewItems.get(position).getTitle());
            Log.d("DB", "holder.item_title.setText(viewItems.get(position).getTitle()); 완료");
            holder.item_date.setText(viewItems.get(position).getDate());
            Log.d("DB", "holder.item_date.setText(viewItems.get(position).getDate()); 완료");
            holder.item_content.setText(viewItems.get(position).getContent());
            Uri uri = Uri.parse(viewItems.get(position).getUri1());
            Log.d("DB", "uri = " + viewItems.get(position).getUri1());

            Log.d("DB", "Uri uri = Uri.parse(viewItems.get(position).getUri1()); 완료");

            Glide.with(context).load(viewItems.get(position).getUri1()).override(600, 200).into(holder.item_image);



    }

    @Override
    public int getItemCount() {
        return viewItems.size();
    }

    public void addItem(ViewData item){
        viewItems.add(item);
        notifyItemChanged(0);

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements Serializable {

        private TextView item_title;
        private TextView item_date;
        private TextView item_content;
        private ImageView item_image;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_title = itemView.findViewById(R.id.item_title);
            item_date = itemView.findViewById(R.id.item_date);
            item_content = itemView.findViewById(R.id.item_content);
            item_image = itemView.findViewById(R.id.item_image);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int curPos = getAdapterPosition();
                    ViewData item = viewItems.get(curPos); //현재 선택한 아이템

                    //취소 팝업 메시지
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("삭제");
                    builder.setMessage("스토리를 삭제하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override //예 버튼 누를 시 삭제
                        public void onClick(DialogInterface dialog, int which) {
                            viewDBHelper.deleteViewData(item.getTitle());
                            viewItems.remove(curPos);
                            notifyItemRemoved(curPos);
                        }
                    });
                    builder.setNegativeButton("아니요", null);
                    builder.setNeutralButton("취소", null);
                    builder.create().show();

                    return true;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() { //아이템 삭제
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), UpdateMain.class);
                    intent.putExtra("title", item_title.getText().toString());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }



}
