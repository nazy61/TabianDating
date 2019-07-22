package com.nazycodes.tabiandating.settings;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.nazycodes.tabiandating.R;
import com.nazycodes.tabiandating.util.FileSearch;

import java.io.File;
import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private static final String TAG = "GalleryFragment";

    //constants
    private static final int NUM_GRID_COLUMNS = 3;
    private static final int NEW_PHOTO_REQUEST = 3567;

    //widgets
    private GridView gridView;
    private ImageView galleryImage;
    private Spinner directorySpinner;

    //vars
    private ArrayList<String> directories;
    private String mSelectedImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        galleryImage = view.findViewById(R.id.galleryImageView);
        gridView = view.findViewById(R.id.gridView);
        directorySpinner = view.findViewById(R.id.spinnerDirectory);
        directories = new ArrayList<>();
        Log.d(TAG, "onCreateView: started");

        ImageView close = view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: closing the gallery fragment");
                getActivity().setResult(NEW_PHOTO_REQUEST);
                getActivity().finish();
            }
        });

        TextView choose = view.findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: photo has been chosen");

                if(!mSelectedImage.equals("")){
                    getActivity().setResult(NEW_PHOTO_REQUEST,
                            getActivity().getIntent().putExtra(getString(R.string.intent_new_gallery_photo), mSelectedImage));
                    getActivity().finish();
                }
            }
        });

        init();

        return view;
    }

    private void init(){
        String rootDir = Environment.getExternalStorageDirectory().getPath();

        //check for other folders inside "/storage/emulated/0/pictures"
        String pictureDir = rootDir + File.separator + "Pictures";
        directories.add(pictureDir);
        if(FileSearch.getDirectoryPaths(pictureDir) != null) {
            directories = FileSearch.getDirectoryPaths(pictureDir);
        }
        String cameraDir = rootDir + File.separator + "DCIM" + File.separator + "Camera";
        directories.add(cameraDir);

        ArrayList<String> directoryNames = new ArrayList<>();
        for(int i = 0; i < directories.size(); i++) {
            Log.d(TAG, "init: directory: " + directories.get(i));
            int index = directories.get(i).lastIndexOf("/");
            String string = directories.get(i).substring(index);
            directoryNames.add(string);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, directoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        directorySpinner.setAdapter(adapter);

        directorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemSelected: selected: " + directories.get(i));

                //setup our image grid for the directory chosen
                setupGridView(directories.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setupGridView(String selectedDirectory){
        Log.d(TAG, "setupGridView: directory chosen: " + selectedDirectory);
        final ArrayList<String> imgURLs = FileSearch.getFilePaths(selectedDirectory);

        if(imgURLs.size() > 0){
            //set the grid column width
            int gridWidth = getResources().getDisplayMetrics().widthPixels;
            int imageWidth = gridWidth/NUM_GRID_COLUMNS;
            gridView.setColumnWidth(imageWidth);

            //use the grid adapter to set the images to gridview
            GridImageAdapter adapter = new GridImageAdapter(getActivity(), R.layout.layout_grid_imageview, imgURLs);
            gridView.setAdapter(adapter);

            //set the first image to be displayed when the activity fragment view is inflated
            try{
                setImage(imgURLs.get(0), galleryImage);
                mSelectedImage = imgURLs.get(0);
            } catch (ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "setupGridView: ArrayIndexOutOfBoundsException: " + e.getMessage());
            }

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "onItemClick: selected an image: " + imgURLs.get(i));

                    setImage(imgURLs.get(i), galleryImage);
                    mSelectedImage = imgURLs.get(i);
                }
            });
        }
    }

    private void setImage(String imgURL, ImageView imageView){
        Log.d(TAG, "setImage: setting image");
        Glide.with(getActivity()).load(imgURL).into(imageView);
    }
}
