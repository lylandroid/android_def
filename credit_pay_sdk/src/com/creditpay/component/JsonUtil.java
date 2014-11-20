/**
 * 文件名称 : JsonUtil.java
 * <p>
 * 作者信息 : liuzongyao
 * <p>
 * 版权声明 : Copyright (c) 2009-2012 HyDb Ltd. All rights reserved
 * <p>
 * 文件描述 : JsonUtils.java - 在这里增加文件描述
 * <p>
 * 评审记录 :
 * <p>
 */
package com.creditpay.component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;

public final class JsonUtil {
	/**
	 * Simple JsonString to obj.
	 * 
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static Object simpleJsonToObject(String jsonString, Class<?> c) {
		try {
			JSONObject obj = new JSONObject(jsonString);
			return toObject(obj, c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * JsonString to obj.
	 * 
	 * @param jsonString
	 * @param c
	 * @return instance of c and values in string.
	 */
	public static Object toObject(String jsonString, Class<?> c) {
		try {
			JSONObject obj = new JSONObject(jsonString);
			return toObject(obj.getJSONObject(c.getSimpleName()), c);
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * 
	 * the topName maybe not same with the class c's simple name.
	 * 
	 * @param jsonString
	 * @param c
	 *            假如是数组就传Example[].class 否则传Example.class 返回的Object是 c的实例
	 * @param topName
	 *            最外层的key.
	 * @return c's new instance.
	 */
	public static Object toObject(String jsonString, Class<?> c, String topName) {
		try {
			if (null == jsonString || jsonString.length() == 0) {
				return null;
			}
			JSONObject obj = new JSONObject(jsonString);
			if (c.isArray()) {
				// array's class name -> [L*******;
				return toObjectArray(
						obj.optJSONArray(topName),
						Class.forName(c.getName().substring(2,
								c.getName().length() - 1)));
			} else {
				return toObject(obj.optJSONObject(topName), c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}

	/**
	 * List to JsonString
	 * 
	 * @param array
	 * @return
	 */
	public static String toJsonStringByList(Object obj) {
		if (!(obj instanceof List)) {
			Log.d("JsonUtil", "obj is not list");
			return null;
		}
		@SuppressWarnings("unchecked")
		List<Object> list = (List<Object>) obj;
		Object[] array = new Object[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = list.get(i);
		}
		String json = toJsonString(array, array.getClass().getSimpleName());
		json = json.substring(json.indexOf(":") + 1, json.length() - 1);
		return json;
	}

	/**
	 * List to JsonString
	 * 
	 * @param array
	 * @return
	 */
	public static String toJsonByStringList(List<String> list) {
		JSONArray array = new JSONArray(list);
		return array.toString();
	}

	/**
	 * List to JsonString
	 * 
	 * @param array
	 * @return
	 */
	public static String toJsonByIntegerList(List<Integer> list) {
		JSONArray array = new JSONArray(list);
		return array.toString();
	}

	/**
	 * object to JsonString
	 * 
	 * @param array
	 * @return
	 */
	public static String toJsonStringByObject(Object object) {

		String json = toJsonString(object);
		json = json.substring(json.indexOf(":") + 1, json.length() - 1);
		return json;
	}

	/**
	 * 
	 * obj to JsonString.
	 * 
	 * @param obj
	 * @return the jsonString of this obj.
	 */
	public static String toJsonString(Object obj) {
		return toJsonString(obj, obj.getClass().getSimpleName());
	}

	/**
	 * 
	 * obj to JsonString.
	 * 
	 * @param obj
	 * @return the jsonString of this obj.
	 */
	public static String toJsonString(Object obj, String topString) {
		JSONObject jobj = new JSONObject();
		try {
			jobj.putOpt(topString, toJson(obj));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return jobj.toString();
	}

	/**
	 * 解析json数组
	 * 
	 * @param array
	 *            json数组
	 * @param c
	 *            封装对象
	 * @return 解析后变成的对象
	 */
	private static Object[] toObjectArray(JSONArray array, Class<?> c) {
		try {
			if (array != null && array.length() > 0) {
				Object[] objArray = (Object[]) Array.newInstance(c,
						array.length());
				for (int i = 0; i < array.length(); i++) {
					if ((array.get(i) instanceof String)
							|| (array.get(i) instanceof Long)
							|| (array.get(i) instanceof Double)
							|| (array.get(i) instanceof Boolean)) {

						objArray[i] = array.get(i);
					} else {
						if (objArray == null) {
							objArray = new Object[array.length()];
						}
						Object innerObj = toObject(array.getJSONObject(i), c);

						if (innerObj != null) {
							objArray[i] = innerObj;
						}
					}
				}
				return objArray;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析json对象
	 * 
	 * @param jobj
	 *            json对象
	 * @param c
	 *            解析成的对象
	 * @return 解析后的对象
	 */
	public static Object toObject(JSONObject jobj, Class<?> c) {
		if (c == null || jobj == null) {
			return null;
		}
		try {
			Object obj = c.newInstance();
			Field[] fields = c.getFields();
			for (Field f : fields) {
				String className = f.getType().getName();
				if (className.equals("int")
						|| className.equals(Integer.class.getName())) {
					f.setInt(obj, jobj.optInt(f.getName()));
				} else if (className.equals("long")
						|| className.equals(Long.class.getName())) {
					f.setLong(obj, jobj.optLong(f.getName()));
				} else if (className.equals("double")
						|| className.equals(Double.class.getName())) {
					f.setDouble(obj, jobj.optDouble(f.getName()));
				} else if (className.equals("boolean")
						|| className.equals(Boolean.class.getName())) {
					f.setBoolean(obj, jobj.optBoolean(f.getName()));
				} else if (className.equals(String.class.getName())) {
					String s = jobj.optString(f.getName());
					if (s != null) {
						f.set(obj, s);
					}
					// if (!TextUtils.isEmpty(s))
					// {
					// f.set(obj, s);
					// }
				} else if (className.startsWith("[L"))// boolean isArray.
				{
					JSONArray array = jobj.optJSONArray(f.getName());
					if (array != null && array.length() > 0) {
						Class<?> innerClass = Class.forName(className
								.substring(2, className.length() - 1));
						Object[] objArray = (Object[]) Array.newInstance(
								innerClass, array.length());
						for (int i = 0; i < array.length(); i++) {
							if ((array.get(i) instanceof String)
									|| (array.get(i) instanceof Long)
									|| (array.get(i) instanceof Double)
									|| (array.get(i) instanceof Boolean)) {

								objArray[i] = array.get(i);
							} else {
								if (objArray == null) {
									objArray = new Object[array.length()];
								}
								Object innerObj = toObject(
										array.getJSONObject(i), innerClass);

								if (innerObj != null) {
									objArray[i] = innerObj;
								}
							}
						}
						f.set(obj, objArray);
					}
				} else {
					JSONObject json = jobj.optJSONObject(f.getName());
					if (json != null) {
						Object innerObj = toObject(json,
								Class.forName(f.getType().getName()));
						if (innerObj != null) {
							f.set(obj, innerObj);
						}
					}

				}
			}
			return obj;
		} catch (Exception e) {
			// Logger.i("JsonUtils", "on parser json  error.....");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * obj to JsonObj.
	 * 
	 * @param obj
	 *            input obj.
	 * @return if obj is array return JsonArray else JsonObject.
	 */
	public static Object toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			if (obj.getClass().isArray()) {
				JSONArray jArray = null;
				if (((Object[]) obj).length > 0) {
					jArray = new JSONArray();
					for (int i = 0; i < ((Object[]) obj).length; i++) {
						if (((Object[]) obj)[i] != null) {
							Class<?> c = obj.getClass();
							if (c == int[].class || c == Integer[].class) {
								jArray.put(((Integer[]) obj)[i]);
							} else if (c == long[].class || c == Long[].class) {
								jArray.put(((Long[]) obj)[i]);
							} else if (c == boolean[].class
									|| c == Boolean[].class) {
								jArray.put(((Boolean[]) obj)[i]);
							} else if (c == double[].class
									|| c == Double[].class) {
								jArray.put(((Double[]) obj)[i]);
							} else if (c == String[].class) {
								jArray.put(((String[]) obj)[i]);
							} else {
								jArray.put(toJson(((Object[]) obj)[i]));
							}
						}
					}
				}
				return jArray;
			}
			JSONObject jobj = new JSONObject();
			Field[] f = obj.getClass().getFields();
			for (Field field : f) {
				Object inner = field.get(obj);
				if (inner == null) {
					continue;
				}
				Class<?> c = inner.getClass();
				if (c == int.class || c == Integer.class) {
					jobj.putOpt(field.getName(), inner);
				} else if (c == long.class || c == Long.class) {
					jobj.putOpt(c.getSimpleName(), inner);
				} else if (c == String.class) {
					if (!"".equals(inner)) {
						jobj.putOpt(field.getName(), inner);
					}
				} else {
					jobj.putOpt(field.getName(), toJson(inner));
				}
			}
			return jobj;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
