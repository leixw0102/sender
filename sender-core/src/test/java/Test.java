/*
 * Copyright 2014 Future TV, Inc.
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.icntv.tv/licenses/LICENSE-1.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.*;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/12/18
 * Time: 15:07
 */
public class Test {
    public static void main(String[]args) throws IOException {
        CharBuffer c = CharBuffer.allocate(50);
        InputStreamReader in = new InputStreamReader(new FileInputStream(new File("d:\\db.txt")));

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("d:\\abc.txt"))));
        while(-1 != in.read(c)){
            Buffer str =  c.flip();
            System.out.print(str.toString());
            c.clear();
            writer.write(str.toString()) ;
        }
//
//        writer.write("bcd\r\n");
//        writer.newLine();
//        writer.write("eee");
        writer.flush();
        writer.close();
    }
}
