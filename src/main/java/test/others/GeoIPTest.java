package test.others;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import com.maxmind.geoip2.record.Location;
import com.maxmind.geoip2.record.Postal;
import com.maxmind.geoip2.record.Subdivision;

public class GeoIPTest {
	public static void main(String[] args) {
		File database = new File("/work/test/0109/GeoIP2-Country.mmdb");
		// This creates the DatabaseReader object. To improve performance, reuse
		// the object across lookups. The object is thread-safe.
		DatabaseReader reader = null;
		try {
			reader = new DatabaseReader.Builder(database).build();
		} catch (IOException e) {
			e.printStackTrace();
		}

		InetAddress ipAddress = null;
		try {
			ipAddress = InetAddress.getByName("128.101.101.101");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// Replace "city" with the appropriate method for your database, e.g.,
		// "country".
		CityResponse response = null;
		try {
			response = reader.city(ipAddress);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeoIp2Exception e) {
			e.printStackTrace();
		}

		Country country = response.getCountry();
		System.out.println(country.getIsoCode());            // 'US'
		System.out.println(country.getName());               // 'United States'
		System.out.println(country.getNames().get("zh-CN")); // '美国'

		Subdivision subdivision = response.getMostSpecificSubdivision();
		System.out.println(subdivision.getName());    // 'Minnesota'
		System.out.println(subdivision.getIsoCode()); // 'MN'

		City city = response.getCity();
		System.out.println(city.getName()); // 'Minneapolis'

		Postal postal = response.getPostal();
		System.out.println(postal.getCode()); // '55455'

		Location location = response.getLocation();
		System.out.println(location.getLatitude());  // 44.9733
		System.out.println(location.getLongitude()); // -93.2323
	}
}
