package pt.neverstopthinking.canticosneocat.ui.Cantico;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.github.chrisbanes.photoview.PhotoView;

import pt.neverstopthinking.canticosneocat.R;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class PhotoFullPopupWindow extends PopupWindow {

    View view;
    Context mContext;
    PhotoView photoView;
    ViewGroup parent;
    private static PhotoFullPopupWindow instance = null;



    public PhotoFullPopupWindow(Context ctx, int layout, View v, String imageUrl, Bitmap bitmap) {
        super(((LayoutInflater) ctx.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate( R.layout.popup_image_full, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        setElevation(5.0f);
        this.mContext = ctx;
        this.view = getContentView();
        ImageButton closeButton = (ImageButton) this.view.findViewById(R.id.ib_close);
        setOutsideTouchable(true);

        setFocusable(true);
        // Set a click listener for the popup window close button
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dismiss();
            }
        });
        //---------Begin customising this popup--------------------

        photoView = (PhotoView) view.findViewById(R.id.cantico_image);
        //photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();
        //----------------------------
        if (bitmap != null) {
            //parent.setBackground(new BitmapDrawable(mContext.getResources(), SyncStateContract.Constants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));// ));
            photoView.setImageBitmap(bitmap);
        } else {
            photoView.setImageResource(R.drawable.abraham);
            showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        //------------------------------

    }

    /*public void onPalette(Palette palette) {
        if (null != palette) {
            ViewGroup parent = (ViewGroup) photoView.getParent().getParent();
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
        }
    }*/
}
