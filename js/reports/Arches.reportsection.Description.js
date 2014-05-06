Ext.define('Arches.reportsection.Description', {
    extend: 'Arches.reportsection.BaseReportSection',

    i18n:{
        gridColumnCond1: "Condition",
        gridColumnCond2: "Date",
        gridColumnMat1: "Component",
        gridColumnMat2: "Material",
        gridColumnMat3: "Techniques",
        gridColumnMat4: "Certainty",
        gridColumnMeas1: "Measurement Type",
        gridColumnMeas2: "Value",
        gridColumnMeas3: "Units",
        gridColumnRelationship: "Relationship",
        gridTitleCond: "Condition History",
        gridTitleMat: "Materials and Techniques",
        gridTitleMeas: "Measured Items",
        title: "Description",
        titleCond: "Condition",
        titleMat: "Materials and Techniques",
        titleMeas: "Measurements"
         },

    initComponent: function () {

        this.i18n = Ext.Object.merge({
            header: 'Base Grid Form Header',
            subheader: 'Base Grid Form Subheader',
            gridColumnRelationship: 'Relationship'
        }, this.i18n);

        this.conditionsGrid = Ext.create('Arches.widgets.Reportgrid',
        {
           gridModel: 'Arches.models.Condition',
           columns:[{
                header: this.i18n.gridColumnCond1,
                dataIndex: 'condition',
                flex: 2
                },{
                header: this.i18n.gridColumnCond2,
                dataIndex: 'date',
                xtype: 'datecolumn',
                format: 'Y-m-d',
                flex: 1
                }],
             entity: this.entity,
             i18n: {
                title: 'Condition',
                gridTitle: this.i18n.gridTitleCond
            }
            });

        this.materialsGrid = Ext.create('Arches.widgets.Reportgrid',
        {
           gridModel: 'Arches.models.Materials',
           columns:[{
                header: this.i18n.gridColumnMat1,
                dataIndex: 'item',
                flex: 2
                },{
                header: this.i18n.gridColumnMat2,
                dataIndex: 'material',
                flex: 1
                },{
                header: this.i18n.gridColumnMat3,
                dataIndex: 'technique',
                flex: 1
                }
                //Start edit: Added certainty column to report
                ,{
                header: this.i18n.gridColumnMat4,
                dataIndex: 'certainty',
                flex: 1
                }
                //End edit
                ],
             entity: this.entity,
             i18n: {
                title: 'Materials and Techniques',
                gridTitle: this.i18n.gridTitleMat
            }
            });

        this.measurementsGrid = Ext.create('Arches.widgets.Reportgrid',
        {
           gridModel: 'Arches.models.Measurements',
           columns:[{
                header: this.i18n.gridColumnMeas1,
                dataIndex: 'type',
                flex: 2
                },{
                header: this.i18n.gridColumnMeas2,
                dataIndex: 'value',
                flex: 1
                },{
                header: this.i18n.gridColumnMeas3,
                dataIndex: 'unit',
                flex: 1
                }],
             entity: this.entity,
             i18n: {
                title: 'Measurements',
                gridTitle: this.i18n.gridTitleMeas
           }
           });

        this.items = [{
                xtype: 'container',
                tpl: '<div class="sectionHeader">' + this.i18n.title + '</div>',
                data: this.entity,
                height: 24
            },
            this.conditionsGrid,
            this.materialsGrid,
            this.measurementsGrid
        ];

        this.callParent(arguments);
    },

    getPrintHtml: function(){
        var html =  
        '<div style="float:left;width:715px">' + 
            '<div class="sectionHeader">' + this.i18n.title + '</div>' +
            '<div>' +
                '<div class="sectionSubHeader">' + 'Condition History' + '</div>' +
                Ext.ux.grid.Printer.print(this.conditionsGrid) + '<br>' +
            '</div>' +
            '<div>' +
                '<div class="sectionSubHeader">' + 'Materials' + '</div>' +
                Ext.ux.grid.Printer.print(this.materialsGrid) + '<br>' +
            '</div>' +
            '<div>' +
                '<div class="sectionSubHeader">' + 'Techniques' + '</div>' +
                Ext.ux.grid.Printer.print(this.techniquesGrid) + '<br>' +
            '</div>' +
            '<div>' +
                '<div class="sectionSubHeader">' + 'Measurements' + '</div>' +
                Ext.ux.grid.Printer.print(this.measurementsGrid) + '<br>' +
            '</div>' +
        '</div>';

        return html;
        }
});