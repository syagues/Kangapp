package projecte.kangapp.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import projecte.kangapp.R;

/**
 * Created by sergi on 24/5/15.
 */
public class CardItem {

    private int itemImageId;
    private Bitmap itemImage;
    private String itemName;
    private String userName;
    private String itemState;
    private String itemPrice;
    private String itemBeginEndDate;
    
    public CardItem (Bitmap itemImage, String itemName, String userName, String itemState, String itemPrice, String itemBeginEndDate){
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.userName = userName;
        this.itemState = itemState;
        this.itemPrice = itemPrice;
        this.itemBeginEndDate = itemBeginEndDate;
    }

    public CardItem (Resources res, int itemImageId, String itemName, String userName, String itemState, String itemPrice, String itemBeginEndDate){
        this.itemImageId = itemImageId;
        this.itemImage = BitmapFactory.decodeResource(res, itemImageId);
        this.itemName = itemName;
        this.userName = userName;
        this.itemState = itemState;
        this.itemPrice = itemPrice;
        this.itemBeginEndDate = itemBeginEndDate;
    }

    public void setItemImageId(int itemImageId) {
        this.itemImageId = itemImageId;
    }

    public int getItemImageId(){
        return itemImageId;
    }

    public void setItemImage(Bitmap itemImage){
        this.itemImage = itemImage;
    }
    
    public Bitmap getItemImage(){
        return itemImage;
    }
    
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    
    public String getItemName(){
        return itemName;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setItemState(String itemState){
        this.itemState = itemState;
    }
    
    public String getItemState(){
        return itemState;
    }
    
    public void setItemPrice(String itemPrice){
        this.itemPrice = itemPrice;
    }
    
    public String getItemPrice(){
        return itemPrice;
    }
    
    public void setItemBeginEndDate(String itemBeginEndDate){
        this.itemBeginEndDate = itemBeginEndDate;
    }
    
    public String getItemBeginEndDate(){
        return itemBeginEndDate;
    }
}
