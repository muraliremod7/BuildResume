package com.example.jntuh.buildresume.adapters;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jntuh.buildresume.R;
import com.example.jntuh.buildresume.ScrollableTabsActivity;
import com.example.jntuh.buildresume.ShowDataActivity;
import com.example.jntuh.buildresume.app.Prefs;
import com.example.jntuh.buildresume.model.EducationModel;
import com.example.jntuh.buildresume.model.OthersModel;
import com.example.jntuh.buildresume.model.ProjectDetailModel;
import com.example.jntuh.buildresume.model.SaveDataModel;
import com.example.jntuh.buildresume.model.WorkExperienceModel;
import com.example.jntuh.buildresume.realm.RealmController;
import com.example.jntuh.buildresume.service.AlertDailogManager;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;


public class BooksAdapter extends RealmRecyclerViewAdapter<SaveDataModel> {

    final Context context;
    private Realm realm;
    private LayoutInflater inflater;
    private PdfWriter pdfWriter;
    public String itemid;
    int ci;
    public String resumeName;
    StringBuilder stringBuilder = new StringBuilder();
    public File sdcard = Environment.getExternalStorageDirectory();
    public BooksAdapter(Context context) {

        this.context = context;
    }
    // create new views (invoked by the layout manager)
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a new card view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewdeatils, parent, false);

        return new CardViewHolder(view);
    }
    // return the size of your data set (invoked by the layout manager)
    public int getItemCount() {

        if (getRealmAdapter() != null) {
            return getRealmAdapter().getCount();
        }else{

        }
        return 0;
    }
    // replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        this.realm = RealmController.getInstance().getRealm();

        // get the article
        final SaveDataModel doctor  = getItem(position);
        // cast the generic view holder to our specific one
        final CardViewHolder holder = (CardViewHolder) viewHolder;

        // set the title and the snippet
        holder.DrName.setText(doctor.getName());
        holder.DrEmail.setText(doctor.getEmail());
        holder.DrMobile.setText(doctor.getMobile());

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(doctor.getPersonpic());
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        holder.imageBackground.setImageBitmap(bitmap);

        holder.deleteDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog1(context,"Are You Sure Delte Resume Details",false);

            }

            public void showAlertDialog1(Context c, String message, final Boolean status) {
                final AlertDialog alertDialog = new AlertDialog.Builder(c,R.style.MyAlertDialogStyle).create();
                // Setting Dialog Message
                alertDialog.setMessage("Are You Sure Delte Resume Details");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        RealmResults<SaveDataModel> results = realm.where(SaveDataModel.class).findAll();
                        // All changes to data must happen in a transaction
                        realm.beginTransaction();
                        results.get(position).getEducationModels().clear();
                        results.get(position).getExperienceModels().clear();
                        results.get(position).getProjectDetailModels().clear();
                        results.get(position).getOthersModelsache().clear();
                        results.get(position).getOthersModelshobbys().clear();
                        results.get(position).getOthersModelsskills().clear();
                        results.get(position).getOthersModelslan().clear();
                        results.get(position).getReferencesModels().clear();
                        results.remove(position);
                        notifyItemRemoved(position);
                        realm.commitTransaction();

                        if (results.size() == 0) {

                            Prefs.with(context).setPreLoad(false);
                        }

                        notifyDataSetChanged();

                        return;

                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.cancel();
                        return;
                    }
                });
                // Showing Alert Message
                alertDialog.show();
            }
        });

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.cardviewdeatils, null);
                int id = doctor.getId();
                itemid = String.valueOf(id);
                Intent intent = new Intent(context,ScrollableTabsActivity.class);
                intent.putExtra("itemid",itemid);
                context.startActivity(intent);
                ((ShowDataActivity)context).finish();
                //-241125681
                //-241100967
            }
        });
        holder.createPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder resumename = new AlertDialog.Builder(context);
                LayoutInflater reli = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View redialogView = reli.inflate(R.layout.addvalue, null);
                resumename.setTitle("Resume Name");
                resumename.setView(redialogView);
                final AlertDialog realertDialog = resumename.create();
                final TextInputLayout inputLayout = (TextInputLayout)redialogView.findViewById(R.id.valueother);
                Button save = (Button)redialogView.findViewById(R.id.save_value);
                Button cancel = (Button)redialogView.findViewById(R.id.cancel_value);

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try{
                            resumeName = inputLayout.getEditText().getText().toString();
                            if(resumeName.equals("")){
                        Toast.makeText(context,"Enter Resume Name",Toast.LENGTH_LONG).show();
                            }else{
                                realertDialog.dismiss();
                                final SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", doctor.getId()).findFirst();
                                String email = book.getEmail();
                                String mobile = book.getMobile();
                                String careerobj = book.getCareerobjective();
                                String declaration = book.getDeclaration();
                                RealmList<EducationModel> models = book.getEducationModels();
                                RealmList<WorkExperienceModel> workExperiencesmodel = book.getExperienceModels();
                                RealmList<ProjectDetailModel> projectDetailModels = book.getProjectDetailModels();
                                RealmList<OthersModel> othersModels = book.getOthersModelsskills();
                                AlertDailogManager dailogManager = new AlertDailogManager();
                                if(email.equals("")||mobile.equals("")||careerobj.equals("")||declaration.equals("")){
                                    dailogManager.showAlertDialog(context,"Enter All fields below  "+"\n"+" Email"+"\n"+" Mobile Number"+"\n"+" Career Objective"+"\n"+" Declaration",false);
                                }else if(models.size()==0){
                                    dailogManager.showAlertDialog(context,"Give Your Education Details",false);
                                }
                                else if(projectDetailModels.size()==0){
                                    dailogManager.showAlertDialog(context,"Give Your Project Details",false);
                                }
                                else if(othersModels.size()==0){
                                    dailogManager.showAlertDialog(context,"Give Your Skills"+"\n"+"Languages"+"\n"+"achievements"+"\n"+"Hobbies",false);
                                }
                                else{

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                    LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View dialogView = li.inflate(R.layout.formatone, null);
                                    builder.setTitle("Choose One Format");
                                    builder.setView(dialogView);
                                    final AlertDialog alertDialog = builder.create();
                                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(context);

                                    final ViewPager mViewPager = (ViewPager) dialogView.findViewById(R.id.pager);
                                    mViewPager.setAdapter(mCustomPagerAdapter);
                                    alertDialog.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ci = mViewPager.getCurrentItem();
                                            alertDialog.dismiss();
                                            if(ci==0){
                                                formateone(book);
                                            }else if(ci==1){
                                                formatetwo(book,position);
                                            }

                                        }
                                    });
                                    alertDialog.show();
                                }
                            }
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        realertDialog.dismiss();
                    }
                });
                realertDialog.show();

                }
        });

    }
        private void formateone(SaveDataModel sv){
            ProgressDialog progressDialog1 = new ProgressDialog(context);//displays the progress bar
            progressDialog1 = ProgressDialog.show(context, "",
                    "Please wait...", true);
            try {
                //creating a directory in SD card
                File mydir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"
                        + "BuildResume"); //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                if (!mydir.exists()) {
                    mydir.mkdirs();
                }
                //getting the full path of the PDF report name
                String mPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/"
                        + "BuildResume" +"/"//PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                        + resumeName+".pdf"; //reportName could be any name

                //constructing the PDF file
                File pdfFile = new File(mPath);

                //Creating a Document with size A4. Document class is available at  com.itextpdf.text.Document
                Document document = new Document(PageSize.A4);
                //assigning a PdfWriter instance to pdfWriter
                pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                pdfWriter.setInitialLeading(12.5f);
                document.open();
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setWidths(new int[]{3, 1});
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(sv.getPersonpic());
                Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);

                ByteArrayInputStream arrayInputStreamm = new ByteArrayInputStream(sv.getSignaturepic());
                Bitmap bitmapp = BitmapFactory.decodeStream(arrayInputStreamm);

                table.addCell(addTitlePage(document,sv.getId()));
                if(bitmap==null){

                }else{
                    table.addCell(createImageCell(bitmap));
                }
                document.add(table);
                //Adding meta-data to the document
                addMetaData(document,sv.getName());
                //Adding Title(s) of the document
                addCareerObjective(document,sv.getId());
                if(sv.getExperienceModels().size()==0){

                }else{
                    addWorkexperience(document,sv.getId());
                }
                //Adding main contents of the document
                addContent(document,sv.getId());

                addskills(document,sv.getId());

                addProjects(document,sv.getId());

                addhobbies(document,sv.getId());
                addachive(document,sv.getId());
                addlanguages(document,sv.getId());
                addpersonalInformation(document,sv.getId());
                adddeclaration(document,sv.getId());
                if(bitmapp==null){

                }else{
                    addsign(document,bitmapp);
                }
                Paragraph p1 = new Paragraph();
                Chunk c1 = new Chunk(sv.getName());
                p1.setAlignment(Element.ALIGN_RIGHT);
                p1.add(c1);
                document.add(p1);
                progressDialog1.dismiss();
                Toast.makeText(context,"Pdf Created",Toast.LENGTH_LONG).show();
                document.close();

                //========================================================================
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void formatetwo(SaveDataModel sv, int position){
            try {

                //creating a directory in SD card
                File mydir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"
                        + "BuildResume"); //PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                if (!mydir.exists()) {
                    mydir.mkdirs();
                }
                //getting the full path of the PDF report name
                String mPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString()+"/"
                        + "BuildResume" +"/"//PATH_PRODUCT_REPORT="/SIAS/REPORT_PRODUCT/"
                        + resumeName+".pdf"; //reportName could be any name

                //constructing the PDF file
                File pdfFile = new File(mPath);

                //Creating a Document with size A4. Document class is available at  com.itextpdf.text.Document
                Document document = new Document(PageSize.A4);
                //assigning a PdfWriter instance to pdfWriter
                pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
                pdfWriter.setInitialLeading(12.5f);
                document.open();

                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(sv.getPersonpic());
                Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);

                ByteArrayInputStream arrayInputStreamm = new ByteArrayInputStream(sv.getSignaturepic());
                Bitmap bitmapp = BitmapFactory.decodeStream(arrayInputStreamm);

                addTitlePageformatone(document,sv.getId(),position);
                //table.addCell(createImageCellformatone(bitmap));
                //Adding meta-data to the document
                addMetaDataformatone(document,sv.getName());
                //Adding Title(s) of the document
                addCareerObjectiveformatone(document,sv.getId());
                if(sv.getExperienceModels().size()==0){

                }else{
                    addWorkexperienceformatone(document,sv.getId());
                }

                //Adding main contents of the document
                addContentformatone(document,sv.getId());

                addskillsformatone(document,sv.getId());

                addProjectsformatone(document,sv.getId());

                addhobbiesformatone(document,sv.getId());
                addachiveformatone(document,sv.getId());
                addpersonalInformationformatone(document,sv.getId());
                adddeclarationformatone(document,sv.getId());
                if(bitmapp==null){

                }else{
                    addsignformatone(document,bitmapp);
                }
                Paragraph p1 = new Paragraph();
                Chunk c1 = new Chunk(sv.getName());
                p1.setAlignment(Element.ALIGN_RIGHT);
                p1.add(c1);
                document.add(p1);
                Toast.makeText(context,"Pdf Created",Toast.LENGTH_LONG).show();
                document.close();

                //========================================================================
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    //formate one data=====================================================================
    private void addsign(Document document, Bitmap id) throws IOException, DocumentException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        id.compress(Bitmap.CompressFormat.PNG, 0, stream);
        Image image = Image.getInstance(stream.toByteArray());
        Paragraph p = new Paragraph();
        p.setSpacingBefore(50);
        image.scaleAbsolute(100, 100);
        Chunk c = new Chunk(image, 0, -24);
        p.add(c);
        p.setAlignment(Element.ALIGN_RIGHT);
        //Add to document
        document.add(p);
    }

    private void addpersonalInformation(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("Personal Details :", red);
        Paragraph elements = new Paragraph(redText);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        Paragraph element = new Paragraph("DateOfBirth     :"+book.getDateofbirth()+"\n"+"Nationality      :"+book.getCountry()+"\n"+"Gender        :"+book.getGender());
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements1,1);
        document.add(element);

    }

    private void addhobbies(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelsache().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelsache().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("Hobbies & Interests :", red);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }
    private void addachive(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelshobbys().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelshobbys().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("Achivements :", red);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }
    private void addlanguages(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelsskills().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelsskills().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("Languages Known :", red);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }
    private void adddeclaration(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Paragraph paragraph = new Paragraph();
        paragraph.setSpacingBefore(15);
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("Declaration :", red);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph(redText);
        //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        childParagraph = new Paragraph();
        childParagraph.add(chunk);
        paragraph.add(childParagraph);
        childParagraph = new Paragraph(book.getDeclaration()); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
        document.add(paragraph);
    }

    private void addskills(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelslan().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelslan().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("Technical Skills :", red);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addProjects(Document document, int id) throws DocumentException {
        String fresher;
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.ORDERED);

        //Adding data into table
        for (int i = 0; i < book.getProjectDetailModels().size(); i++) { //
            unorderedList.add(new ListItem("Project Title : "+book.getProjectDetailModels().get(i).getProjecttitle()+"\n"+"Role :"+book.getProjectDetailModels().get(i).getYourrole()+"\n"+"Project Duration :"+book.getProjectDetailModels().get(i).getDurationfrom()+"-"+book.getProjectDetailModels().get(i).getDurationto()+"\n"+"Project Description : "+book.getProjectDetailModels().get(i).getProjectdesc()+"\n"+"\n"));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        if(book.getExperienceModels().size()==0){
            fresher = "Academic Project :";
        }else{
            fresher = "Projects Undertaken :";
        }
        Chunk redText = new Chunk(fresher, red);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addWorkexperience(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(7);
        PdfPCell cell = new PdfPCell();
        //Adding data into table
        List unorderedList = new List(List.ORDERED);
        for (int i = 0; i < book.getExperienceModels().size(); i++) { //
            if(book.getExperienceModels().get(i).getTowork().equals("Present")){
                unorderedList.add(new ListItem("Currently I am Working as a "+book.getExperienceModels().get(i).getJobtitle()+" In "+book.getExperienceModels().get(i).getCompanyname()+" since "+book.getExperienceModels().get(i).getFromwork()+" to "+book.getExperienceModels().get(i).getTowork()));
            }
            else{
                unorderedList.add(new ListItem("I was Worked in "+book.getExperienceModels().get(i).getCompanyname()+" as a "+book.getExperienceModels().get(i).getJobtitle()+" Since "+book.getExperienceModels().get(i).getFromwork()+" to "+book.getExperienceModels().get(i).getTowork()));
            }
        }

        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("WorkExperience :", red);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addCareerObjective(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Paragraph paragraph = new Paragraph();
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("CareerObjective :", red);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph(redText);
        //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
        childParagraph.setSpacingBefore(20);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        childParagraph = new Paragraph();
        childParagraph.add(chunk);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph(book.getCareerobjective()); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
        document.add(paragraph);

    }

    private static void addMetaData(Document document, String name) {
        document.addTitle("All Product Names");
        document.addSubject("none");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("SIAS ERP");
        document.addCreator(name);
    }

    public static PdfPCell createImageCell(Bitmap path) throws DocumentException, IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        path.compress(Bitmap.CompressFormat.PNG, 0, stream);
        Image image = Image.getInstance(stream.toByteArray());
        image.setAbsolutePosition(100f, 550f);
        //Scale to new height and new width of image
        image.scaleAbsolute(150, 150);
        PdfPCell cell = new PdfPCell(image, true);
        return cell;
    }

    private static PdfPCell addTitlePage(Document document, int id)
            throws DocumentException {
        PdfPCell cell = new PdfPCell();
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Paragraph paragraph = new Paragraph();
        Font f=new Font(Font.FontFamily.TIMES_ROMAN,30.0f);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph(book.getName(),f); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph(book.getEmail()); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        childParagraph.setSpacingBefore(5);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph(book.getMobile());
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        childParagraph.setSpacingBefore(5);

        paragraph.add(childParagraph);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(paragraph);
        cell.setBorder(Rectangle.NO_BORDER);
        document.add(cell);
        return cell;
    }
    /**
     * In this method the main contents of the documents are added
     * @param document
     * @param id
     * @throws DocumentException
     */

    private static void addContent(Document document, int id) throws DocumentException {
        Font font = new Font(Font.FontFamily.TIMES_ROMAN);
        Paragraph reportBody = new Paragraph();
        reportBody.setFont(font); //public static Font FONT_BODY = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.NORMAL);

        // Creating a table
        createTable(reportBody,id);

        // now add all this to the document
        document.add(reportBody);

    }
    /**
     * This method is used to add empty lines in the document
     * @param paragraph
     * @param number
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    /**
     * This method is responsible to add table using iText
     * @param reportBody
     * @throws BadElementException
     */
    private static void createTable(Paragraph reportBody,int id)
            throws BadElementException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        float[] columnWidths = {5,5,5,5,5}; //total 4 columns and their width. The first three columns will take the same width and the fourth one will be 5/2.
        PdfPTable table = new PdfPTable(columnWidths);

        table.setWidthPercentage(100); //set table with 100% (full page)
        table.getDefaultCell().setUseAscender(true);


        //Adding table headers
        PdfPCell cell = new PdfPCell(new Phrase("Qualification")); //Public static Font FONT_TABLE_HEADER = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER); //alignment
        cell.setBackgroundColor(new GrayColor(0.75f)); //cell background color
        cell.setFixedHeight(30); //cell height
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Institute"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("University"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Year"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Percentage"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(new GrayColor(0.75f));
        cell.setFixedHeight(30);
        table.addCell(cell);
        //End of adding table headers

        //Adding data into table
        for (int i = 0; i < book.getEducationModels().size(); i++) { //
            cell = new PdfPCell(new Phrase(book.getEducationModels().get(i).getQualification()));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(book.getEducationModels().get(i).getInstitute()));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(book.getEducationModels().get(i).getBoardUniversity()));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(book.getEducationModels().get(i).getPassingYear()));
            cell.setFixedHeight(28);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(book.getEducationModels().get(i).getPercentage()));
            cell.setFixedHeight(28);
            table.addCell(cell);
        }
        addEmptyLine(reportBody,1);
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("EdcationQaulification :", red);
        Paragraph elements = new Paragraph(redText);
        elements.setAlignment(Element.ALIGN_LEFT);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        reportBody.add(elements);
        reportBody.add(elements1);
        addEmptyLine(reportBody,1);
        reportBody.add(table);

    }
//formate one data=====================================================================

    private void addsignformatone(Document document, Bitmap id) throws IOException, DocumentException {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        id.compress(Bitmap.CompressFormat.PNG, 0, stream);
        Image image = Image.getInstance(stream.toByteArray());
        Paragraph p = new Paragraph();
        p.setSpacingBefore(50);
        image.scaleAbsolute(100, 100);
        Chunk c = new Chunk(image, 0, -24);
        p.add(c);
        p.setAlignment(Element.ALIGN_RIGHT);
        //Add to document
        document.add(p);
    }

    private void addpersonalInformationformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("PERSONAL INFORMATION", red);
        Paragraph elements = new Paragraph(redText);
        redText.setBackground(BaseColor.LIGHT_GRAY,160,6,160,6);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        elements.setSpacingBefore(15);
        for(int i=0;i<book.getOthersModelsskills().size();i++){
            stringBuilder.append(book.getOthersModelsskills().get(i).getValue());
            stringBuilder.append(", ");

        }
        elements.setAlignment(Element.ALIGN_CENTER);
        Paragraph element = new Paragraph("DateOfBirth                  :  "+book.getDateofbirth()+"\n"+"Nationality                   :  "+book.getCountry()+"\n"+"Gender                        :  "+book.getGender()+"\n"+"Languages Known         :  "+stringBuilder.toString());
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements1,1);
        document.add(element);

    }

    private void addhobbiesformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelsache().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelsache().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("HOBBIES&INTERESTS", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,180,6,180,6);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_CENTER);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addachiveformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelshobbys().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelshobbys().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("ACHIVEMENTS", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,200,6,200,6);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_CENTER);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void adddeclarationformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Paragraph paragraph = new Paragraph();
        paragraph.setSpacingBefore(15);
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("DECLARATION", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,200,6,200,6);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph(redText);
        //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        childParagraph = new Paragraph();
        childParagraph.add(chunk);
        paragraph.add(childParagraph);
        childParagraph = new Paragraph(book.getDeclaration()); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
        document.add(paragraph);
    }

    private void addskillsformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.UNORDERED);
        //Adding data into table
        for (int i = 0; i < book.getOthersModelslan().size(); i++) { //
            unorderedList.add(new ListItem(book.getOthersModelslan().get(i).getValue()));
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("TECHNICAL SKILLS", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,180,6,180,6);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_CENTER);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addProjectsformatone(Document document, int id) throws DocumentException {
        String fresher;
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        List unorderedList = new List(List.ORDERED);
        //Adding data into table
        for (int i = 0; i < book.getProjectDetailModels().size(); i++) { //
            unorderedList.add(new ListItem("Project Title : "+book.getProjectDetailModels().get(i).getProjecttitle()+"                                                   "+book.getProjectDetailModels().get(i).getDurationfrom()+"-"+book.getProjectDetailModels().get(i).getDurationto()+"\n"+"Role :"+book.getProjectDetailModels().get(i).getYourrole()+"\n"+"Team Members : "+book.getProjectDetailModels().get(i).getTeammem()+"\n"+"Project Description : "+book.getProjectDetailModels().get(i).getProjectdesc()+"\n"+"\n"));
        }
        if(book.getExperienceModels().size()==0){
            fresher = "Academic Project :";
        }else{
            fresher = "PROJECTS PROFILE";
        }
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk(fresher, red);
        redText.setBackground(BaseColor.LIGHT_GRAY,180,6,180,6);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_CENTER);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addContentformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        //Adding data into table
        List unorderedList = new List(List.ORDERED);
        for (int i = 0; i < book.getEducationModels().size(); i++) { //

                unorderedList.add(new ListItem(book.getEducationModels().get(i).getInstitute()+" - "+book.getEducationModels().get(i).getBoardUniversity()+"\n"+"PassedOut Year :  "+book.getEducationModels().get(i).getPassingYear()+"\n\n"+"I did my "+book.getEducationModels().get(i).getQualification()+" with "+book.getEducationModels().get(i).getPercentage()+"%"+"\n\n"));
        }

        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("EDUCATION", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,220,6,220,6);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_CENTER);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addWorkexperienceformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        //Adding data into table
        List unorderedList = new List(List.ORDERED);
        for (int i = 0; i < book.getExperienceModels().size(); i++) { //
            if(!book.getExperienceModels().get(i).getTowork().equals("Present")){
                unorderedList.add(new ListItem(book.getExperienceModels().get(i).getCompanyname()+"\n"+"Duration :  "+book.getExperienceModels().get(i).getFromwork()+" to "+book.getExperienceModels().get(i).getTowork()+"\n\n"+"Job Description : "+book.getExperienceModels().get(i).getJobdescription()+"\n\n"));
            }
            else{
                unorderedList.add(new ListItem(book.getExperienceModels().get(i).getCompanyname()+"\n"+"Duration :  "+book.getExperienceModels().get(i).getFromwork()+" to "+book.getExperienceModels().get(i).getTowork()+"\n\n"+"Job Description : "+book.getExperienceModels().get(i).getJobdescription()+"\n\n"));
            }
        }

        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("PROFESSIONAL EXPERIENCE", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,150,6,150,6);
        Paragraph elements = new Paragraph(redText);
        elements.setSpacingBefore(15);
        elements.setAlignment(Element.ALIGN_CENTER);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        Paragraph elements1 = new Paragraph();
        elements1.add(chunk);
        document.add(elements);
        document.add(elements1);
        addEmptyLine(elements,1);
        document.add(unorderedList);
    }

    private void addCareerObjectiveformatone(Document document, int id) throws DocumentException {
        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Paragraph paragraph = new Paragraph();
        Font red = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, BaseColor.BLACK);
        Chunk redText = new Chunk("CAREER OBJECTIVE", red);
        redText.setBackground(BaseColor.LIGHT_GRAY,180,6,180,6);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph(redText);
        //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(childParagraph);
        childParagraph.setSpacingBefore(20);
        LineSeparator ls = new LineSeparator();
        Chunk chunk = new Chunk(ls);
        childParagraph = new Paragraph();
        childParagraph.add(chunk);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph(book.getCareerobjective()); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(childParagraph);
        document.add(paragraph);

    }

    private static void addMetaDataformatone(Document document, String name) {
        document.addTitle("All Product Names");
        document.addSubject("none");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("SIAS ERP");
        document.addCreator(name);
    }

    private void addTitlePageformatone(Document document,  int id,int doctorId)
            throws DocumentException {

        Realm realm = RealmController.getInstance().getRealm();
        SaveDataModel book = realm.where(SaveDataModel.class).equalTo("id", id).findFirst();
        Paragraph paragraph = new Paragraph();
        Font f=new Font(Font.FontFamily.TIMES_ROMAN,30.0f, Font.BOLD);
        Font f1=new Font(Font.FontFamily.TIMES_ROMAN,20.0f, Font.BOLD);
        // Adding several title of the document. Paragraph class is available in  com.itextpdf.text.Paragraph
        Paragraph childParagraph = new Paragraph(book.getName(),f); //public static Font FONT_TITLE = new Font(Font.FontFamily.TIMES_ROMAN, 22,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_CENTER);
        childParagraph.setSpacingAfter(20);
        paragraph.add(childParagraph);
        childParagraph = new Paragraph("Email : "+book.getEmail()); //public static Font FONT_SUBTITLE = new Font(Font.FontFamily.TIMES_ROMAN, 18,Font.BOLD);
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        childParagraph.setSpacingBefore(5);
        paragraph.add(childParagraph);

        childParagraph = new Paragraph("Mobile : "+book.getMobile());
        childParagraph.setAlignment(Element.ALIGN_LEFT);
        childParagraph.setSpacingBefore(5);

        paragraph.add(childParagraph);
        paragraph.setAlignment(Element.ALIGN_CENTER);

        document.add(paragraph);
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {

        public CardView card;
        public TextView DrName;
        public TextView DrEmail;
        public TextView DrMobile;
        public TextView itemid;
        public ImageView imageBackground,deleteDoctor,signpic;
        public LinearLayout createPdf;
        public CardViewHolder(View itemView) {
            // standard view holder pattern with Butterknife view injection
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_books);
            DrName = (TextView) itemView.findViewById(R.id.personName);
            DrEmail = (TextView) itemView.findViewById(R.id.personEmail);
            DrMobile = (TextView) itemView.findViewById(R.id.personMobile);
            itemid = (TextView) itemView.findViewById(R.id.itemid);
            imageBackground = (ImageView) itemView.findViewById(R.id.profile_image);
            deleteDoctor = (ImageView)itemView.findViewById(R.id.deleterecycler);
            createPdf = (LinearLayout)itemView.findViewById(R.id.createPdf);


        }
    }
    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        int[] mResources = {
                R.drawable.formatone,
                R.drawable.formattwo

        };
        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mResources.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            imageView.setImageResource(mResources[position]);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
