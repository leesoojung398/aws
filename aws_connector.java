/*
 * Copyright 2010-2018 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 *  This file was modified by SeHoon Yang(sehoon.yang@e4net.net), under Apache2.0 License
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class aws_connector
{
    BasicAWSCredentials credentials;
    AmazonS3 s3;

    /* Referenced by the test function */
    public static String bucketName = "e4net-1";

    public aws_connector()
    {
        try
        {
            credentials = new BasicAWSCredentials("AKIAJQDTNAIJB6HQK4IQ\r\n", "v/lNn/XznmEeV57gLC1FnVe59Dr1chgB+AXYWalO\r\n");
        }
        catch (Exception e)
        {
            throw new AmazonClientException("Key error", e);
        }
        AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        s3 = AmazonS3ClientBuilder.standard().withCredentials(credentialsProvider).withRegion("ap-northeast-2").build();
    }

    public boolean validate_user(String id, String pw) throws Exception
    {
        Class.forName("org.mariadb.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://sdfg.cpe9zasmdf6y.ap-northeast-2.rds.amazonaws.com:3306/aws_server_db", "soojung", "12345678");
        
        String query = "SELECT * FROM user WHERE user_name='"+id+"' AND user_pwd='"+pw+"'";
        
        Statement stmt = null;
        int cnt = -1;
        
        try {
        	stmt = connection.createStatement();
        	ResultSet rs = stmt.executeQuery(query);
        	while(rs.next()) {
            	cnt = 1;
            }
        }catch (Exception e) {
			// TODO: handle exception
		}finally {
			if(stmt != null) {
				stmt.close();
			}
		}
        
        stmt.close();
        connection.close();
        
        if(cnt != 1) {
        	return false;
        }else {
        	return true;
        }

    }

    /*
     *  Reference function
     */
    private static File createSampleFile() throws IOException
    {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
}
