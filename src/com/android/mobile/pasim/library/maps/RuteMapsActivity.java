package com.android.mobile.pasim.library.maps;

import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.widget.Toast;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.android.mobile.pasim.library.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class RuteMapsActivity extends FragmentActivity {
	Location location;
	private GoogleMap mMap; // Might be null if Google Play services APK is not
	ArrayList<LatLng> directionPoint;
	int size_of_latlong, latlong_index = 0;
	ArrayList<Polyline> polylines;
	SensorManager sensor_manager;
	// private int result=0;
	MarkerOptions mo, end_mo;
	Marker my_marker, end_marker;
	boolean first_time_flag = false, update_flag = false;

	LatLng my_latlong, end_latlong;
	private static final int MAP_TYPE_HYBRID = 0x00000004;

//	private static final int MAP_TYPE_DEFAULT = 0x00000004;
	// available.
	ArrayList<LatLng> point;
	Document doc;
	String direction = "walking";
		
	// pasim
	// -6.8927406
	// 107.5817809,17

	// bea cukai
	// -6.2062869
	// 106.8753595

	// monas
//	double latTujuan = -6.1750359;
//	double lngTujuan = 106.827192;

	//perpus pasim
//	double latTujuan = -6.8945481;
//	double lngTujuan = 107.5711535;

	double latTujuan = -6.894495;
	double lngTujuan = 107.571733;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rute_maps);
		// setUpMapIfNeeded();

		polylines = new ArrayList<Polyline>();

		end_latlong = new LatLng(latTujuan, lngTujuan);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor(getString(R.color.actionBar_color))));
		actionBar
				.setTitle(Html
						.fromHtml("<font weight='bold' color='#fe0000'>LOKASI</font>"));
		actionBar.setIcon(R.drawable.circle_maps);
		actionBar.setDisplayHomeAsUpEnabled(true);

		// batas baru
		mMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		if (mMap == null) {
			Toast.makeText(getApplicationContext(), "Sorry Map Not Load",
					Toast.LENGTH_LONG).show();
		} else {
			mMap.setMyLocationEnabled(true);
			mMap.getUiSettings().setZoomControlsEnabled(true);
			mMap.getUiSettings().setMyLocationButtonEnabled(true);
			mMap.setMapType(MAP_TYPE_HYBRID);

			mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {

				@Override
				public void onMyLocationChange(Location location) {
					// TODO Auto-generated method stub

					if (update_flag == true) {
						my_latlong = new LatLng(location.getLatitude(),
								location.getLongitude());

						if (latlong_index == size_of_latlong) {
							// speakOut("you are very close to your friend.");
							return;
						}

						float bearing = getBearing(my_latlong,
								directionPoint.get(latlong_index));
						String name = "user";
						mo = new MarkerOptions()
								.position(my_latlong)
								.title(name + ", you are here!")
								.snippet("" + my_latlong)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.icon_location))
								.flat(true).rotation(bearing + 180);
						my_marker.remove();
						my_marker = mMap.addMarker(mo);
						// map.moveCamera(CameraUpdateFactory.newLatLng(my_latlong));

						CameraPosition.Builder cameraBuilder = new CameraPosition.Builder()
								.target(my_latlong).bearing(bearing);

						cameraBuilder.tilt(mMap.getCameraPosition().tilt);
						cameraBuilder.zoom(mMap.getCameraPosition().zoom);
						CameraPosition cameraPosition = cameraBuilder.build();

						mMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));

						if (getDistanceinDouble(
								directionPoint.get(latlong_index), my_latlong) < 15.0f) {
							polylines.get(latlong_index).remove();
							latlong_index++;
						}

						if (getDistanceinDouble(
								directionPoint.get(latlong_index), my_latlong) > 200.0f) {
							first_time_flag = false;
							update_flag = false;
							return;
						}

						if (latlong_index < size_of_latlong) {
							String direction = getDirection(my_latlong,
									directionPoint.get(latlong_index));
							Toast.makeText(
									getApplicationContext(),
									"Go to "
											+ direction
											+ " ("
											+ getDistance(my_latlong,
													end_latlong) + ")",
									Toast.LENGTH_LONG).show();
							// speakOut("Go to "+direction);
						}

					}

					if (first_time_flag == false) {
						latlong_index = 0;
						my_latlong = new LatLng(location.getLatitude(),
								location.getLongitude());
						mo = new MarkerOptions()
								.position(my_latlong)
								.title("denny" + ", you are here!")
								.snippet("" + my_latlong)
								.icon(BitmapDescriptorFactory
										.fromResource(R.drawable.icon_location))
								.flat(true).rotation(245);
						end_mo = new MarkerOptions()
								.position(end_latlong)
								.title("this" + " is here!")
								.snippet("" + end_latlong)
								.icon(BitmapDescriptorFactory
										.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

						mMap.clear();
						my_marker = mMap.addMarker(mo);
						end_marker = mMap.addMarker(end_mo);

						mMap.moveCamera(CameraUpdateFactory
								.newLatLng(my_latlong));
						mMap.animateCamera(CameraUpdateFactory.zoomTo(18));

						new Request_Update().execute(location);
						first_time_flag = true;
						update_flag = true;
					}
				}
			});

			// sorry di hide dulu
			// mMap.setOnMyLocationChangeListener(new
			// OnMyLocationChangeListener() {
			//
			// @Override
			// public void onMyLocationChange(Location location) {
			// // TODO Auto-generated method stub
			//
			// String name = "denny";
			// LatLng loc = new LatLng(location.getLatitude(),
			// location.getLongitude());
			// MarkerOptions mo = new MarkerOptions().position(loc).title(name +
			// ", you are here!")
			// .snippet("" + loc);
			// mMap.clear();
			// mMap.addMarker(mo);
			// mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
			// mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
			// PolylineOptions polylineOptions = new PolylineOptions();
			//
			// // Setting the color of the polyline
			// polylineOptions.color(Color.GREEN);
			//
			// // Setting the width of the polyline
			// polylineOptions.width(3);
			// polylineOptions.add(new LatLng(location.getLatitude(),
			// location.getLongitude()),
			// new LatLng(latTujuan, lngTujuan));
			//
			// mMap.addPolyline(polylineOptions);
			//
			// Toast.makeText(getApplicationContext(), "POSITION HERE " + loc,
			// Toast.LENGTH_LONG).show();
			//
			// }
			// });
		}

	}

	private float getBearing(LatLng begin, LatLng end) {

		double lat = Math.abs(begin.latitude - end.latitude);
		double lng = Math.abs(begin.longitude - end.longitude);

		if (begin.latitude < end.latitude && begin.longitude < end.longitude)
			return (float) (Math.toDegrees(Math.atan(lng / lat)));
		else if (begin.latitude >= end.latitude
				&& begin.longitude < end.longitude)
			return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
		else if (begin.latitude >= end.latitude
				&& begin.longitude >= end.longitude)
			return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
		else if (begin.latitude < end.latitude
				&& begin.longitude >= end.longitude)
			return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);

		return -1;
	}

	private String getDistance(LatLng my_latlong, LatLng end_latlong) {
		Location l1 = new Location("One");
		l1.setLatitude(my_latlong.latitude);
		l1.setLongitude(my_latlong.longitude);

		Location l2 = new Location("Two");
		l2.setLatitude(end_latlong.latitude);
		l2.setLongitude(end_latlong.longitude);

		float distance = l1.distanceTo(l2);
		String dist = distance + " M";

		if (distance > 1000.0f) {
			distance = distance / 1000.0f;
			dist = distance + " KM";
		}
		return dist;
	}

	private float getDistanceinDouble(LatLng my_latlong, LatLng frnd_latlong) {
		Location l1 = new Location("One");
		l1.setLatitude(my_latlong.latitude);
		l1.setLongitude(my_latlong.longitude);

		Location l2 = new Location("Two");
		l2.setLatitude(frnd_latlong.latitude);
		l2.setLongitude(frnd_latlong.longitude);

		float distance = l1.distanceTo(l2);

		return distance;
	}

	private String getDirection(LatLng my_latlong, LatLng end_latlong) {
		// TODO Auto-generated method stub
		double my_lat = my_latlong.latitude;
		double my_long = my_latlong.longitude;

		double end_lat = end_latlong.latitude;
		double end_long = end_latlong.longitude;

		double radians = getAtan2((end_long - my_long), (end_lat - my_lat));
		double compassReading = radians * (180 / Math.PI);

		String[] coordNames = { "North", "North-East", "East", "South-East",
				"South", "South-West", "West", "North-West", "North" };
		int coordIndex = (int) Math.round(compassReading / 45);

		if (coordIndex < 0) {
			coordIndex = coordIndex + 8;
		}
		;

		return coordNames[coordIndex]; // returns the coordinate value
	}

	private double getAtan2(double longi, double lat) {
		return Math.atan2(longi, lat);
	}

	// method get polyline
	public class Request_Update extends AsyncTask<Location, Void, Location> {
		@Override
		protected void onPreExecute() {
			// Toast.makeText(getApplicationContext(), "onPreExecute()!",
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		protected Location doInBackground(Location... location) {
			// TODO Auto-generated method stub

			String url = "http://maps.googleapis.com/maps/api/directions/xml?"
					+ "origin=" + location[0].getLatitude() + ","
					+ location[0].getLongitude() + "&destination=" + latTujuan
					+ "," + lngTujuan + "&sensor=false&units=metric&mode="
					+ direction; // direction="walking"
									// or
									// "driving"

			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpContext localContext = new BasicHttpContext();
				HttpPost httpPost = new HttpPost(url);
				HttpResponse response = httpClient.execute(httpPost,
						localContext);
				InputStream in = response.getEntity().getContent();
				DocumentBuilder builder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
				doc = builder.parse(in);
			} catch (Exception e) {
			}

			return location[0];
		}

		@Override
		protected void onPostExecute(Location location) {
			if (doc != null) {
				directionPoint = getDirection(doc);
				int ii = 0;
				size_of_latlong = directionPoint.size();
				for (; ii < size_of_latlong; ii++) {
					if (ii == 0) {
						PolylineOptions rectLine = new PolylineOptions().width(
								8).color(Color.RED);
						rectLine.add(my_latlong, directionPoint.get(ii));
						Polyline polyline = mMap.addPolyline(rectLine);
						polylines.add(polyline);
					} else {
						PolylineOptions rectLine = new PolylineOptions().width(
								8).color(Color.RED);
						rectLine.add(directionPoint.get(ii - 1),
								directionPoint.get(ii));
						Polyline polyline = mMap.addPolyline(rectLine);
						polylines.add(polyline);
					}
				}
				PolylineOptions rectLine = new PolylineOptions().width(8)
						.color(Color.RED);
				rectLine.add(end_latlong, directionPoint.get(ii - 1));
				Polyline polyline = mMap.addPolyline(rectLine);
				polylines.add(polyline);
				// map.addPolyline(rectLine);
			}
		}
	}

	public ArrayList<LatLng> getDirection(Document doc) {
		NodeList nl1, nl2, nl3;
		ArrayList<LatLng> listGeopoints = new ArrayList<LatLng>();
		nl1 = doc.getElementsByTagName("step");
		if (nl1.getLength() > 0) {
			for (int i = 0; i < nl1.getLength(); i++) {
				Node node1 = nl1.item(i);
				nl2 = node1.getChildNodes();

				Node locationNode = nl2
						.item(getNodeIndex(nl2, "start_location"));
				nl3 = locationNode.getChildNodes();
				Node latNode = nl3.item(getNodeIndex(nl3, "lat"));
				double lat = Double.parseDouble(latNode.getTextContent());
				Node lngNode = nl3.item(getNodeIndex(nl3, "lng"));
				double lng = Double.parseDouble(lngNode.getTextContent());
				listGeopoints.add(new LatLng(lat, lng));

				locationNode = nl2.item(getNodeIndex(nl2, "polyline"));
				nl3 = locationNode.getChildNodes();
				latNode = nl3.item(getNodeIndex(nl3, "points"));
				ArrayList<LatLng> arr = decodePoly(latNode.getTextContent());
				for (int j = 0; j < arr.size(); j++) {
					listGeopoints.add(new LatLng(arr.get(j).latitude, arr
							.get(j).longitude));
				}

				locationNode = nl2.item(getNodeIndex(nl2, "end_location"));
				nl3 = locationNode.getChildNodes();
				latNode = nl3.item(getNodeIndex(nl3, "lat"));
				lat = Double.parseDouble(latNode.getTextContent());
				lngNode = nl3.item(getNodeIndex(nl3, "lng"));
				lng = Double.parseDouble(lngNode.getTextContent());
				listGeopoints.add(new LatLng(lat, lng));
			}
		}
		return listGeopoints;
	}

	private int getNodeIndex(NodeList nl, String nodename) {
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeName().equals(nodename))
				return i;
		}
		return -1;
	}

	private ArrayList<LatLng> decodePoly(String encoded) {
		ArrayList<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;
		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;
			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng position = new LatLng(lat / 1E5, lng / 1E5);
			poly.add(position);
		}
		return poly;
	}

	// batas komen----
	//
	// @Override
	// protected void onResume() {
	// super.onResume();
	// setUpMapIfNeeded();
	// }
	// private void setUpMapIfNeeded() {
	// // Do a null check to confirm that we have not already instantiated the
	// map.
	// if (mMap == null) {
	// // Try to obtain the map from the SupportMapFragment.
	// mMap = ((SupportMapFragment)
	// getSupportFragmentManager().findFragmentById(R.id.map))
	// .getMap();
	// // Check if we were successful in obtaining the map.
	// if (mMap != null) {
	// // setUpMap();
	// getPolyline();
	// }
	// }
	// }
	//
	//
	// private void setUpMap() {
	//
	// mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
	// LatLng(-8.1235303, 110.5161231),15));
	//
	// }
	//
	// private void zoomMap(){
	//
	//
	// mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new
	// LatLng(location.getLatitude(), location.getLongitude()), 13));
	// mMap.addMarker(new MarkerOptions()
	// .position(new LatLng(location.getLatitude(), location.getLongitude()))
	// .title("Starting Point")
	// .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_outline))
	// );
	// double lat = location.getLatitude();
	// double lng = location.getLongitude();
	//
	// Log.d("LAT", String.valueOf(lat));
	// Log.d("LNG",String.valueOf(lng));
	// }
	// private void getPolyline(){
	// // -6.218335 106.802216 GBK
	// //Monas -6.1750359 and 106.827192
	// PolylineOptions polylineOptions = new PolylineOptions();
	//
	// // Setting the color of the polyline
	// polylineOptions.color(Color.BLUE);
	//
	// // Setting the width of the polyline
	// polylineOptions.width(3);
	// polylineOptions.add(new LatLng(-37.81319, 144.96298),new
	// LatLng(-31.95285, 115.85734));
	// mMap.addPolyline(polylineOptions);
	//
	//
	// }

	// / function udah ok ----

	// mMap = ((SupportMapFragment)
	// getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	//
	// if (mMap == null) {
	// Toast.makeText(getApplicationContext(), "Sorry Map Not Load",
	// Toast.LENGTH_LONG).show();
	// } else {
	// mMap.setMyLocationEnabled(true);
	// mMap.getUiSettings().setZoomControlsEnabled(true);
	// mMap.getUiSettings().setMyLocationButtonEnabled(true);
	//
	//
	// mMap.setOnMyLocationChangeListener(new OnMyLocationChangeListener() {
	//
	// @Override
	// public void onMyLocationChange(Location location) {
	// // TODO Auto-generated method stub
	//
	// String name="denny";
	// LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
	// MarkerOptions mo=new MarkerOptions().position(loc).title(name+", you are
	// here!").snippet(""+loc);
	// mMap.clear();
	// mMap.addMarker(mo);
	// mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
	// mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
	//
	//
	// }
	// });
	// }
	//
	// }

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// MenuInflater inflater = getMenuInflater();
	// inflater.inflate(R.menu.reload_only, menu);
	// return super.onCreateOptionsMenu(menu);
	// }
	//
	// @Override
	// public boolean onOptionsItemSelected(MenuItem item) {
	// switch (item.getItemId()) {
	// case R.id.reload:
	// refreshMenuItem = item;
	// new SyncData().execute();
	// return true;
	// default:
	// return super.onOptionsItemSelected(item);
	// }
	// }
	//
	// private class SyncData extends AsyncTask<String, Void, String> {
	// @Override
	// protected void onPreExecute() {
	// refreshMenuItem.setActionView(R.layout.action_progressbar);
	// refreshMenuItem.expandActionView();
	// }
	//
	// @Override
	// protected String doInBackground(String... params) {
	// try {
	// Thread.sleep(3000);
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(String result) {
	// refreshMenuItem.collapseActionView();
	// refreshMenuItem.setActionView(null);
	// }
	// };

}
