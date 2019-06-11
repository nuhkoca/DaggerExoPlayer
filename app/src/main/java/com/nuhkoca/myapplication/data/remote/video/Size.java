package com.nuhkoca.myapplication.data.remote.video;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nuhkoca.myapplication.BR;

public class Size extends BaseObservable {

  @Expose @SerializedName("link_with_play_button") private String linkWithPlayButton;

  @Bindable public String getLinkWithPlayButton() {
    return linkWithPlayButton;
  }

  public void setLinkWithPlayButton(String linkWithPlayButton) {
    this.linkWithPlayButton = linkWithPlayButton;
    notifyPropertyChanged(BR.linkWithPlayButton);
  }
}
