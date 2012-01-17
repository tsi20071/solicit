package br.edu.ifpb.solicit.support;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

@Service("md5Calculator")
public class Md5Calculator implements Serializable {
	private Md5Calculator() {}

	public String md5(String input) {
		String result = "";
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(input.getBytes());
			byte[] md5 = algorithm.digest();
			String temp = "";
			for (int i = 0; i < md5.length; i++) {
				temp = (Integer.toHexString(0xFF & md5[i]));
				if (temp.length() == 1) {
					result += "0" + temp;
				} else {
					result += temp;
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}
}
