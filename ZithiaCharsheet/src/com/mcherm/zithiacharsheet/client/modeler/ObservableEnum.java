/*
 * Copyright 2009 Michael Chermside
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
package com.mcherm.zithiacharsheet.client.modeler;

/**
 * An observable of some enumerated type, T.
 */
public interface ObservableEnum<T extends Enum<T>> extends Observable {
    public T getValue();

    /**
     * Returns the Class object for the Enum that is being observed.
     * This is needed because Java's implementation of generics uses
     * type erasure, so without some way to access the class we can't
     * do things like listing all the possible values of the enum.
     *
     * @return the class object for T.
     */
    public Class<T> getEnumClass();
}