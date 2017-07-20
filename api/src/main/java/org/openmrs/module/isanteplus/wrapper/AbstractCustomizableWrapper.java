/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.isanteplus.wrapper;

import org.openmrs.OpenmrsObject;
import org.openmrs.attribute.Attribute;
import org.openmrs.attribute.AttributeType;
import org.openmrs.customdatatype.Customizable;
import org.openmrs.module.metadatadeploy.handler.AbstractObjectDeployHandler;

/**
 * Abstract base class for wrappers of customizable (i.e. have attributes) objects
 */
public abstract class AbstractCustomizableWrapper<T extends OpenmrsObject & Customizable<A>, A extends Attribute> extends AbstractObjectWrapper<T> {

	/**
	 * Creates a new wrapper
	 * @param target the target object
	 */
	public AbstractCustomizableWrapper(T target) {
		super(target);
	}

	/**
	 * Convenience method to get the value of the first attribute of the given type
	 * @param attrTypeUuid the attribute type UUID
	 * @return the value or null
	 */
	protected Object getAsAttribute(String attrTypeUuid) {
		A attr = findFirstAttribute(attrTypeUuid);
		return attr != null ? attr.getValue() : null;
	}

	/**
	 * Gets the value of the first active attribute of the given type. By using the UUID of the attribute type this
	 * avoids forcing us to keep re-fetching attribute types via service methods which can be slow.
	 * @param attrTypeUuid the attribute type UUID
	 * @return the value or null
	 */
	protected A findFirstAttribute(String attrTypeUuid) {
		if (target.getAttributes() != null) {
			for (A attr : target.getAttributes()) {
				if (attr.getAttributeType().getUuid().equals(attrTypeUuid) && !attr.isVoided()) {
					return attr;
				}
			}
		}
		return null;
	}
}