/*
ARCHES - a program developed to inventory and manage immovable cultural heritage.
Copyright (C) 2013 J. Paul Getty Trust and World Monuments Fund

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/

Ext.define('Arches.forms.ArchaeologicalMaterials', {
    extend: 'Arches.forms.Materials',
    alias: 'form.arches-forms-archaeologicalmaterials',

    gridModel: 'Arches.models.ArchaeologicalMaterials',
    itemConceptType: 'ARCHAEOLOGICAL COMPONENT TYPE.E55',
    materialConceptType: 'MATERIAL.E57',
    techniqueConceptType: 'ARCHAEOLOGICAL TECHNIQUE.E55',
    certaintyConceptType: 'COMPONENT CERTAINTY TYPE.E55'        //Edit: Added certaintyConceptType; is used in Arches.forms.Materials.js
});
