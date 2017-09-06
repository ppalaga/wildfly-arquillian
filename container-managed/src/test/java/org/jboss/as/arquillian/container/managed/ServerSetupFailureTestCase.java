/*
 * Copyright 2017 Red Hat, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jboss.as.arquillian.container.managed;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.as.arquillian.api.ServerSetup;
import org.jboss.as.arquillian.api.ServerSetupTask;
import org.jboss.as.arquillian.container.ManagementClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test methods should not be called at all when a {@code ServerSetupTask#setup(ManagementClient, String)} throws and exception
 *
 * @author <a href="https://github.com/ppalaga">Peter Palaga</a>
 */
@RunWith(Arquillian.class)
@ServerSetup(ServerSetupFailureTestCase.SetupTask.class)
public class ServerSetupFailureTestCase {
    static class SetupTask implements ServerSetupTask {

        @Override
        public void setup(ManagementClient managementClient, String containerId) throws Exception {
            /* It is this failure that should make the whole test fail */
            Assert.fail(SetupTask.class.getSimpleName() +".setup() failed");
        }

        @Override
        public void tearDown(ManagementClient managementClient, String containerId) throws Exception {
        }

    }

    @Test
    public void shouldNotBeInvokedAtAll() {
        Assert.fail("A test method should not be invoked after a ServerSetupTask.setup() failure");
    }
}
