package com.strato.hidrive.views.exif_info;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;

public class ExifMetaData {

	private long ctime;
	private long mtime;
	private String name;
	private Long size;
	private Integer ImageWidth = -1;
	private Integer ImageHeight = -1;
	private long ExifImageWidth;
	private long ExifImageHeight;
	private double Aperture = -1d;
	private String ExposureTime;
	private int ISO = -1;
	private double FocalLength = -1d;
	private int Orientation;
	private int XResolution;
	private int YResolution;
	private int ResolutionUnit;
	private int BitsPerSample;
	@Nullable
	private Double GPSLatitude = null;
	@Nullable
	private Double GPSLongitude = null;
	@Nullable
	private Double GPSAltitude = null;
	@Nullable
	private Date DateTimeOriginal;
	private String Make;
	private String Model;
	private String locationText;
	private String path;

	public static class Builder {
		private long ctime;
		private long mtime;
		private String name;
		private Long size;
		private Integer imageWidth;
		private Integer imageHeight;
		private long exifImageWidth;
		private long exifImageHeight;
		private double aperture = -1d;
		private String exposureTime;
		private int iso = -1;
		private double focalLength = -1d;
		private int orientation;
		private int xResolution;
		private int yResolution;
		private int resolutionUnit;
		private int bitsPerSample;
		@Nullable
		private Double gpsLatitude = null;
		@Nullable
		private Double gpsLongitude = null;
		@Nullable
		private Double gpsAltitude = null;
		private String dateTimeOriginal;
		private String make;
		private String model;
		private String locationText;
		private String path;

		public Builder ctime(long val) {
			this.ctime = val;
			return this;
		}

		public Builder mtime(long val) {
			this.mtime = val;
			return this;
		}

		public Builder name(String val) {
			this.name = val;
			return this;
		}

		public Builder size(Long val) {
			this.size = val;
			return this;
		}

		public Builder imageWidth(Integer val) {
			this.imageWidth = val;
			return this;
		}

		public Builder imageHeight(Integer val) {
			this.imageHeight = val;
			return this;
		}

		public Builder exifImageWidth(long val) {
			this.exifImageWidth = val;
			return this;
		}

		public Builder exifImageHeight(long val) {
			this.exifImageHeight = val;
			return this;
		}

		public Builder aperture(double val) {
			this.aperture = val;
			return this;
		}

		public Builder exposureTime(String val) {
			this.exposureTime = val;
			return this;
		}

		public Builder iso(int val) {
			this.iso = val;
			return this;
		}

		public Builder focalLength(double val) {
			this.focalLength = val;
			return this;
		}

		public Builder orientation(int val) {
			this.orientation = val;
			return this;
		}

		public Builder xResolution(int val) {
			this.xResolution = val;
			return this;
		}

		public Builder yResolution(int val) {
			this.yResolution = val;
			return this;
		}

		public Builder resolutionUnit(int val) {
			this.resolutionUnit = val;
			return this;
		}

		public Builder bitsPerSample(int val) {
			this.bitsPerSample = val;
			return this;
		}

		public Builder gpsLatitude(@Nullable Double val) {
			this.gpsLatitude = val;
			return this;
		}

		public Builder gpsLongitude(@Nullable Double val) {
			this.gpsLongitude = val;
			return this;
		}

		public Builder gpsAltitude(@Nullable Double val) {
			this.gpsAltitude = val;
			return this;
		}

		public Builder dateTimeOriginal(String val) {
			this.dateTimeOriginal = val;
			return this;
		}

		public Builder make(String val) {
			this.make = val;
			return this;
		}

		public Builder model(String val) {
			this.model = val;
			return this;
		}

		public Builder locationText(String val) {
			this.locationText = val;
			return this;
		}

		public Builder path(String val) {
			this.path = val;
			return this;
		}

		public ExifMetaData build() {
			return new ExifMetaData(this);
		}
	}

	private ExifMetaData(Builder builder) {
		ctime = builder.ctime;
		mtime = builder.mtime;
		name = builder.name;
		size = builder.size;
		ImageWidth = builder.imageWidth;
		ImageHeight = builder.imageHeight;
		ExifImageWidth = builder.exifImageWidth;
		ExifImageHeight = builder.exifImageHeight;
		Aperture = builder.aperture;
		ExposureTime = builder.exposureTime;
		ISO = builder.iso;
		FocalLength = builder.focalLength;
		Orientation = builder.orientation;
		XResolution = builder.xResolution;
		YResolution = builder.yResolution;
		ResolutionUnit = builder.resolutionUnit;
		BitsPerSample = builder.bitsPerSample;
		GPSLatitude = builder.gpsLatitude;
		GPSLongitude = builder.gpsLongitude;
		GPSAltitude = builder.gpsAltitude;
		DateTimeOriginal = parseDateTimeOriginal(builder.dateTimeOriginal);
		Make = builder.make;
		Model = builder.model;
		locationText = builder.locationText;
		path = builder.path;
	}

	public ExifMetaData(String path, long lastModified, boolean isDirectory, long contentLength) {
		this.path = path;
		this.DateTimeOriginal = new Date(lastModified);
		if (!isDirectory) {
			this.size = contentLength;
		}
	}

	@Nullable
	private Date parseDateTimeOriginal(@Nullable String stringDateField) {
		if (stringDateField != null && !stringDateField.isEmpty()) {
			SimpleDateFormat dateParser = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);
			try {
				return dateParser.parse(stringDateField);
			} catch (ParseException e) {
				Log.e(getClass().getSimpleName(), "", e);
			}
		}
		return null;
	}

	public long getCtime() {
		return ctime;
	}

	public long getMtime() {
		return mtime;
	}

	public String getName() {
		return name;
	}

	public Long getSize() {
		return size;
	}

	public Integer getImageWidth() {
		return ImageWidth;
	}

	public Integer getImageHeight() {
		return ImageHeight;
	}

	public long getExifImageWidth() {
		return ExifImageWidth;
	}

	public long getExifImageHeight() {
		return ExifImageHeight;
	}

	public double getAperture() {
		return Aperture;
	}

	public String getExposureTime() {
		return ExposureTime;
	}

	public int getISO() {
		return ISO;
	}

	public double getFocalLength() {
		return FocalLength;
	}

	public int getOrientation() {
		return Orientation;
	}

	public int getXResolution() {
		return XResolution;
	}

	public int getYResolution() {
		return YResolution;
	}

	public int getResolutionUnit() {
		return ResolutionUnit;
	}

	public int getBitsPerSample() {
		return BitsPerSample;
	}

	@Nullable
	public Double getGPSLatitude() {
		return GPSLatitude;
	}

	@Nullable
	public Double getGPSLongitude() {
		return GPSLongitude;
	}

	@Nullable
	public Double getGPSAltitude() {
		return GPSAltitude;
	}

	public Date getDateTimeOriginal() {
		return DateTimeOriginal;
	}

	public String getMake() {
		return Make;
	}

	public String getModel() {
		return Model;
	}

	public void setLocationText(String locationText) {
		this.locationText = locationText;
	}

	public String getLocationText() {
		return locationText;
	}

	public String getPath() {
		return path;
	}

	public void formatLocationTextIfPossible(Context context) {
		if (getGPSLatitude() != null && getGPSLongitude() != null) {
			setLocationText(String.format(Locale.US, "%f, %f", getGPSLatitude(), getGPSLongitude()));

			if (Geocoder.isPresent()) {
				Geocoder gcd = new Geocoder(context, Locale.US);
				List<Address> addresses = null;
				try {
					addresses = gcd.getFromLocation(getGPSLatitude(), getGPSLongitude(), 1);
				} catch (IOException e) {
					Log.e(getClass().getSimpleName(), "", e);
				} catch (IllegalArgumentException e) {
					Log.w(getClass().getSimpleName(), e.toString());
					setLocationText(null);
				}
				if (addresses != null && addresses.size() > 0) {
					Address address = addresses.get(0);
					String locality = "";
					if (address.getLocality() != null) {
						locality = address.getLocality();
					} else if (address.getMaxAddressLineIndex() >= 1) {
						locality = address.getAddressLine(address.getMaxAddressLineIndex() - 1);
					}
					if (address.getCountryName() != null) {
						if (locality.length() > 0) {
							locality += ", ";
						}
						locality += address.getCountryName();
					}
					setLocationText(locality);
				}
			}
		}
	}

}
