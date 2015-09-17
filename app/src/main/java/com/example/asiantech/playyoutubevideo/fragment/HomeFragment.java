package com.example.asiantech.playyoutubevideo.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.asiantech.playyoutubevideo.PlayVideoActivity_;
import com.example.asiantech.playyoutubevideo.R;
import com.example.asiantech.playyoutubevideo.adapter.CustomVideoHome;
import com.example.asiantech.playyoutubevideo.core.Callback;
import com.example.asiantech.playyoutubevideo.core.api.AuthApi;
import com.example.asiantech.playyoutubevideo.object.Items;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import retrofit.RetrofitError;


/**
 * Created by anhtt on 04/09/2015.
 *
 * @author tienanh
 * @version 1.0
 */
@EFragment(R.layout.fragmenthome)
public class HomeFragment extends Fragment implements CustomVideoHome.OnPlayYtbViDeo {

    private List<Items.ItemsEntity> mItems = new ArrayList<>();
    private CustomVideoHome mAdapterVideo;
    @ViewById(R.id.recyclerViewVideo)
    RecyclerView mRecyclerViewVideo;
    private ProgressDialog mProgressDialog;
    public static final String PLAYLIST_KEY = "PLdYSWqTrWP2jyqWIdjsATbrb11uN_BMrF";
    public static final String API_KEY = "AIzaSyBtEYk0NLi1DcEjYx8Z1TDDWulmmTajV4s";
    public static final int MAX_ITEMS = 41;
    public static final String ITEM_TYPE = "video";
    public static final String API_SNIPPET = "snippet";


    @AfterViews
    void contentLayout() {
        loadData();
        contentAdapter();
    }

    private void contentAdapter() {
        StaggeredGridLayoutManager mGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerViewVideo.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewVideo.setHasFixedSize(true);
        mRecyclerViewVideo.setLayoutManager(mGridLayoutManager);
        mAdapterVideo = new CustomVideoHome(getActivity(), mItems, this);
        mRecyclerViewVideo.setAdapter(mAdapterVideo);
    }

    private void loadData() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCanceledOnTouchOutside(false);
        if (!mProgressDialog.isShowing())
            mProgressDialog.show();
        AuthApi.getAllVideo(PLAYLIST_KEY, API_SNIPPET, MAX_ITEMS, ITEM_TYPE, API_KEY, new Callback<Items>() {
            @Override
            public void success(Items item) {
                mItems.addAll(item.getItems());
                mAdapterVideo.notifyDataSetChanged();
                if (getActivity() != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error, Error myError) {
                if (getActivity() != null && mProgressDialog.isShowing())
                    mProgressDialog.dismiss();
            }
        });
    }

    @Override
    public void OnPlayVideo(String videoId, int position, String urlImageThumb) {
//        DialogPlayVideo dialogReviewImage = DialogPlayVideo_.builder()
//                .ytbVideoId(videoId)
//                .ytbVideoThumb(urlImageThumb)
//                .build();
//        dialogReviewImage.show(getFragmentManager(), "DialogShowVideo");
        PlayVideoActivity_.intent(getActivity())
                .extra("Video_ID", videoId)
                .extra("Video_Thumbs", urlImageThumb)
                .start();
        getActivity().overridePendingTransition(R.anim.right_to_left, R.anim.left_to_right);
    }
}