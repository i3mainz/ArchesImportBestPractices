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

Ext.define('Arches.forms.Materials', {
    extend: 'Arches.forms.BaseGridForm',
    alias: 'form.arches-forms-materials',

    i18n: {
        header: 'Materials',
        subheader: 'Materials used for this resource',
        additembuttontext: 'New Material',
        gridColumn1: 'Item',
        gridColumn2: 'Material',
        gridColumn3: 'Technique',
        gridColumn4: 'Certainty'            //Edit: Added certainty column
    },

    tabIconCls: 'glyph-pinetree',

    gridModel: 'Arches.models.Materials',
    itemConceptType: 'ARCHAEOLOGICAL COMPONENT TYPE.E55',
    materialConceptType: 'MATERIAL.E57',

    initComponent: function () {
        this.columns = [{
                header: this.i18n.gridColumn1,
                dataIndex: 'item',
                flex: 2,
                renderer: this.renderConceptColumn,
                editor: {
                    xtype: 'conceptcombobox',
                    resourceEntityId: this.controller.assetentitytypeid,
                    conceptEntityId: this.itemConceptType,
                    gridform: this,
                    allowBlank: false
                }
            },{
                header: this.i18n.gridColumn2,
                dataIndex: 'material',
                flex: 2,
                renderer: this.renderConceptColumn,
                editor: {
                    xtype: 'conceptcombobox',
                    resourceEntityId: this.controller.assetentitytypeid,
                    conceptEntityId: this.materialConceptType,
                    gridform: this,
                    allowBlank: false
                }
            },{
                header: this.i18n.gridColumn3,
                dataIndex: 'technique',
                flex: 2,
                renderer: this.renderConceptColumn,
                editor: {
                    xtype: 'conceptcombobox',
                    resourceEntityId: this.controller.assetentitytypeid,
                    conceptEntityId: this.techniqueConceptType,
                    gridform: this,
                    allowBlank: false
                }
            }
            //Start edit: Add certainty column in Material and Techniques form
            ,{
                header: this.i18n.gridColumn4,
                dataIndex: 'certainty',
                flex: 2,
                renderer: this.renderConceptColumn,
                editor: {
                    xtype: 'conceptcombobox',
                    resourceEntityId: this.controller.assetentitytypeid,
                    conceptEntityId: this.certaintyConceptType,             //certaintyConceptType is defined in Arches.forms.ArchaeologicalMaterials.js
                    gridform: this,
                    allowBlank: false
                }
            //End edit
            }
        ];

        this.callParent(arguments);
    }
});
