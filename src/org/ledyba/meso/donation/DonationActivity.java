package org.ledyba.meso.donation;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.ledyba.meso.R;

import com.android.vending.billing.IInAppBillingService;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class DonationActivity extends Activity {
	private IInAppBillingService billingService_ = null;
	private ServiceConnection serviceConnection_ = null;
	
	private final static String TAG="DonationActivity";

	public DonationActivity() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donation);
	}

	@Override
	protected void onResume() {
		super.onResume();

		this.serviceConnection_ = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				Log.d(TAG, "ServiceConnected: "+name);
				DonationActivity.this.billingService_ = IInAppBillingService.Stub.asInterface(service);

			}

			@Override
			public void onServiceDisconnected(ComponentName name) {
				Log.d(TAG, "ServiceDisconnected: "+name);
				DonationActivity.this.billingService_ = null;
			}
		};

		// バインドする
		this.bindService(new Intent("com.android.vending.billing.InAppBillingService.BIND"), this.serviceConnection_, Context.BIND_AUTO_CREATE);
		Log.d(TAG, "Bind service: InAppBillingService");
	}

	@Override
	protected void onPause() {
		if (this.serviceConnection_ != null) {
			unbindService(this.serviceConnection_);
			Log.d(TAG, "Unbind service: InAppBillingService");
			this.serviceConnection_ = null;
		}
		super.onPause();
	}
	
	static final int BillingVersion = 3;
	static final class Product {
		private final String productId_;
		private final String title_;
		private final String description_;
		private final String price_;
		private final String type_;
		public Product(final String productId, final String type, final String price, final String title, final String desc){
			this.productId_ = productId;
			this.type_ = type;
			this.price_ = price;
			this.title_ = title;
			this.description_ = desc;
		}
		public static Product fromString( final String detail ) throws JSONException{
			JSONObject object = new JSONObject( detail );
			final String productId = object.getString("productId");
			final String type = object.getString("type");
			final String price = object.getString("price");
			final String title = object.getString("title");
			final String description = object.getString("description");
			return new Product(productId, type, price, title, description);
		}
		public String getProductId() {
			return productId_;
		}
		public String getTitle() {
			return title_;
		}
		public String getDescription() {
			return description_;
		}
		public String getPrice() {
			return price_;
		}
		public String getType() {
			return type_;
		}
		
		
	}
	
	public boolean isBillingSupported(){
		try {
			final int supported = this.billingService_.isBillingSupported(BillingVersion, getPackageName(), "inapp");
			if( supported == 0 ) {
				return true;
			}else{
				
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<Product> fetchProducts( final List<String> ids ){
		final Bundle bun = new Bundle();
		bun.putStringArrayList("ITEM_ID_LIST", new ArrayList<String>(ids));
		List<Product> r = new ArrayList<DonationActivity.Product>();
		try {
			final Bundle res = this.billingService_.getSkuDetails(BillingVersion, getPackageName(), "inapp", bun);
			final int respCode = res.getInt("RESPONSE_CODE");
			if( respCode != 0 ) { //error
				
			}
			final List<String> details = res.getStringArrayList("DETAILS_LIST");
			for( String detail : details ){
				r.add(Product.fromString(detail));
			}
			return r;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
