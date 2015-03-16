package org.app.anoopam.thakorjitoday;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.app.anoopam.R;
import org.app.anoopam.ViewPagerT;

public class AMViewAdapter extends FragmentPagerAdapter {

    private int[] offerImages = { R.drawable.settings, R.drawable.settings, R.drawable.settings, R.drawable.settings, R.drawable.settings };

    private Bitmap[] bitter = {};

    public void setImages(int[] Img) {
        offerImages = Img;
    }

    public void setImagesB(Bitmap[] Img) {
        bitter = Img;
    }

    private int mCount = offerImages.length;

    public AMViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new ViewPagerT(bitter[position]);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        if (count > 0 && count <= 12) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
