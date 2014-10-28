package tv.icntv.sender;/*
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


import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import tv.icntv.sender.conf.Configuration;

import java.util.Map;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/10/21
 * Time: 10:30
 */
public abstract class SenderModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(Configuration.class).in(Scopes.SINGLETON);
    }

}
