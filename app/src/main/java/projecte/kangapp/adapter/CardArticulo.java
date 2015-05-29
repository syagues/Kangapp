package projecte.kangapp.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import projecte.kangapp.R;

/**
 * Created by sergi on 24/5/15.
 */
public class CardArticulo {

    private int articuloId;
    private String pathArticuloImage;
    private int articuloImageId;
    private Bitmap articuloImage;
    private String articuloName;
    private String articuloType;
    private String userName;
    private String articuloPrice;
    private String articuloBeginEndDate;
    private String articuloState;

    public CardArticulo(int articuloId, String pathArticuloImage, String articuloName, String articuloType, String userName, String articuloPrice, String articuloBeginEndDate, String articuloState){

        this.articuloId = articuloId;
        this.pathArticuloImage = pathArticuloImage;
        this.articuloName = articuloName;
        this.articuloType = articuloType;
        this.userName = userName;
        this.articuloPrice = articuloPrice;
        this.articuloBeginEndDate = articuloBeginEndDate;
        this.articuloState = articuloState;
    }

    public CardArticulo(int articuloId, Bitmap articuloImage, String articuloName, String articuloType, String userName, String articuloPrice, String articuloBeginEndDate, String articuloState){

        this.articuloId = articuloId;
        this.articuloImage = articuloImage;
        this.articuloName = articuloName;
        this.articuloType = articuloType;
        this.userName = userName;
        this.articuloPrice = articuloPrice;
        this.articuloBeginEndDate = articuloBeginEndDate;
        this.articuloState = articuloState;
    }

    public CardArticulo(int articuloId, Resources res, int articuloImageId, String articuloName, String articuloType, String userName, String articuloPrice, String articuloBeginEndDate, String articuloState){

        this.articuloId = articuloId;
        this.articuloImageId = articuloImageId;
        this.articuloImage = BitmapFactory.decodeResource(res, articuloImageId);
        this.articuloName = articuloName;
        this.articuloType = articuloType;
        this.userName = userName;
        this.articuloPrice = articuloPrice;
        this.articuloBeginEndDate = articuloBeginEndDate;
        this.articuloState = articuloState;
    }

    public void setArticuloId(int articuloId) {
        this.articuloId = articuloId;
    }

    public int getArticuloId() {
        return articuloId;
    }

    public void setPathArticuloImage(String pathArticuloImage) {
        this.pathArticuloImage = pathArticuloImage;
    }

    public String getPathArticuloImage() {
        return pathArticuloImage;
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
