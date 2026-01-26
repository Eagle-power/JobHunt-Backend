package com.jobhunt.utility;

public class Data {
	public static String getMessageBody(String otp , String name) { 

		return 
		        "<!DOCTYPE html>" +
		        "<html lang=\"en\">" +
		        "<head>" +
		        "  <meta charset=\"UTF-8\" />" +
		        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>" +
		        "  <title>OTP Verification</title>" +
		        "</head>" +
		        "<body style=\"margin:0; padding:0; background-color:#f4f6f8; font-family:Arial, Helvetica, sans-serif;\">" +

		        "<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">" +
		        "  <tr>" +
		        "    <td align=\"center\" style=\"padding:40px 10px;\">" +

		        "      <table width=\"100%\" style=\"max-width:600px; background:#ffffff; border-radius:8px; box-shadow:0 4px 12px rgba(0,0,0,0.08);\">" +

		        "        <tr>" +
		        "          <td style=\"padding:24px; text-align:center; background:#1e40af; color:#ffffff; border-radius:8px 8px 0 0;\">" +
		        "            <h1 style=\"margin:0; font-size:22px;\">JobHunt</h1>" +
		        "          </td>" +
		        "        </tr>" +

		        "        <tr>" +
		        "          <td style=\"padding:30px; color:#333333;\">" +
		        "            <p style=\"font-size:16px; margin:0 0 12px;\">Hi "+name+",</p>" +
		        "            <p style=\"font-size:15px; line-height:1.6; margin:0 0 20px;\">" +
		        "              Use the following One-Time Password (OTP) to verify your email address. " +
		        "              This OTP is valid for the next <strong>10 minutes</strong>." +
		        "            </p>" +

		        "            <div style=\"text-align:center; margin:30px 0;\">" +
		        "              <span style=\"" +
		        "                display:inline-block;" +
		        "                font-size:28px;" +
		        "                letter-spacing:6px;" +
		        "                font-weight:bold;" +
		        "                color:#1e40af;" +
		        "                padding:12px 24px;" +
		        "                border:1px dashed #1e40af;" +
		        "                border-radius:6px;" +
		        "              \">" +
		        						otp +
		        "              </span>" +
		        "            </div>" +

		        "            <p style=\"font-size:14px; color:#555555; line-height:1.6;\">" +
		        "              If you didn’t request this verification, you can safely ignore this email." +
		        "            </p>" +

		        "            <p style=\"font-size:14px; margin-top:30px;\">" +
		        "              Thanks,<br/>" +
		        "              <strong>JobHunt Team</strong>" +
		        "            </p>" +
		        "          </td>" +
		        "        </tr>" +

		        "        <tr>" +
		        "          <td style=\"padding:18px; text-align:center; background:#f1f5f9; font-size:12px; color:#666;\">" +
		        "            © 2025 JobHunt. All rights reserved." +
		        "          </td>" +
		        "        </tr>" +

		        "      </table>" +
		        "    </td>" +
		        "  </tr>" +
		        "</table>" +

		        "</body>" +
		        "</html>";

	}
}
