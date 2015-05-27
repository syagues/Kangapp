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
public class CardArticulo {

    private int articuloImageId;
    private Bitmap articuloImage;
    private String articuloName;
    private String articuloType;
    private String userName;
    private String articuloPrice;
    private String articuloBeginEndDate;
    private String articuloState;

    public CardArticulo(Bitmap articuloImage, String articuloName, String articuloType, String userName, String articuloPrice, String articuloBeginEndDate, String articuloState){
        this.articuloImage = articuloImage;
        this.articuloName = articuloName;
        this.articuloType = articuloType;
        this.userName = userName;
        this.articuloPrice = articuloPrice;
        this.articuloBeginEndDate = articuloBeginEndDate;
        this.articuloState = articuloState;
    }

    public CardArticulo(Resources res, int articuloImageId, String articuloName, String articuloType, String userName, String articuloPrice, String articuloBeginEndDate, String articuloState){
        this.articuloImageId = articuloImageId;
        this.articuloImage = BitmapFactory.decodeResource(res, articuloImageId);
        this.articuloName = articuloName;
        this.articuloType = articuloType;
        this.userName = userName;
        this.articuloPrice = articuloPrice;
        this.articuloBeginEndDate = articuloBeginEndDate;
        this.articuloState = articuloState;
    }

    public void setArticuloImageId(int articuloImageId) {
        this.articuloImageId = articuloImageId;
    }

    public int getArticuloImageId(){
        return articuloImageId;
    }

    public void setArticuloImage(Bitmap articuloImage){
        this.articuloImage = articuloImage;
    }
    
    public Bitmap getArticuloImage(){
        return articuloImage;
    }
    
    public void setArticuloName(String articuloName){
        this.articuloName = articuloName;
    }
    
    public String getArticuloName(){
        return articuloName;
    }

    public void setArticuloType(String articuloType){
        this.articuloType = articuloType;
    }

    public String getArticuloType(){
        return articuloType;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setArticuloPrice(String articuloPrice){
        this.articuloPrice = articuloPrice;
    }

    public String getArticuloPrice(){
        return articuloPrice;
    }

    public void setArticuloBeginEndDate(String articuloBeginEndDate){
        this.articuloBeginEndDate = articuloBeginEndDate;
    }

    public String getArticuloBeginEndDate(){
        return articuloBeginEndDate;
    }

    public void setArticuloState(String articuloState){
        this.articuloState = articuloState;
    }

    public String getArticuloState(){
        return articuloState;
    }
}
